package org.th.pokefight.core.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.th.pokefight.core.PokeFightConstants;
import org.th.pokefight.core.PokeFightProperties;
import org.th.pokefight.core.exception.NoSuchPokemonException;
import org.th.pokefight.core.model.Pokemon;
import org.th.pokefight.core.service.PokemonService;

import jakarta.validation.constraints.NotNull;

@Service
public class PokemonServiceImpl implements PokemonService {

    private static final String ID_PLACEHOLDER = "{id}";

    private final PokeFightProperties pokeFightProperties;
    private final RestTemplate restTemplate;

    private final Map<String, Pokemon> pokemonPowerCache = new ConcurrentHashMap<>();

    @Autowired
    public PokemonServiceImpl(PokeFightProperties pokeFightProperties, RestTemplate restTemplate) {
        this.pokeFightProperties = pokeFightProperties;
        this.restTemplate = restTemplate;
    }

    @Override
    public Pokemon getRandomPokemon() {
        int randomPokemonId = generateRandomPokemonId();
        Pokemon result = getPokemon(String.valueOf(randomPokemonId));
        result.setPower(generateRandomPower(null, null));
        pokemonPowerCache.put(result.getName(), result);
        return result;
    }

    private int generateRandomPower(Integer minPower, Integer maxPower) {
        Integer max = PokeFightConstants.POWER_MAX;
        if (maxPower != null) {
            max = maxPower;
        }
        Integer min = PokeFightConstants.POWER_MIN;
        if (minPower != null) {
            min = minPower;
        }
        return new Random().nextInt(min, max);
    }

    private int generateRandomPokemonId() {
        return new Random().nextInt(PokeFightConstants.POKEMON_ID_MIN, PokeFightConstants.POKEMON_ID_MAX);
    }

    @Override
    public Pokemon find(String name) {
        return pokemonPowerCache.computeIfAbsent(name, this::getPokemon);
    }

    @Override
    public void removeFromCache(List<String> names) {
        names.forEach(pokemonPowerCache::remove);
    }

    /** Lucky that we can use id OR name to query the poke API */
    private Pokemon getPokemon(String nameOrId) {
        String url = pokeFightProperties.getPokeApiUrlPrefix() + ID_PLACEHOLDER;
        Pokemon result = restTemplate.getForObject(url, Pokemon.class, nameOrId);
        if (result == null) {
            // cannot be null, exception thrown in restTemplate.getForObject call above
            throw new NoSuchPokemonException(nameOrId);
        }
        return result;
    }

    @Override
    public @NotNull Pokemon getRandomPokemon(Integer maxPower) {
        int randomPokemonId = generateRandomPokemonId();
        Pokemon result = getPokemon(String.valueOf(randomPokemonId));
        Integer minPower = null;
        if (maxPower == null) {
            if (hasType(result, PokeFightConstants.TYPE_FLYING)) {
                maxPower = PokeFightConstants.TYPE_FLYING_MAX_POWER;
                minPower = PokeFightConstants.TYPE_FLYING_MIN_POWER;
            }
            if (hasType(result, PokeFightConstants.TYPE_FIGHTING)) {
                maxPower = PokeFightConstants.TYPE_FIGHTING_MAX_POWER;
                minPower = PokeFightConstants.TYPE_FIGHTING_MIN_POWER;
            }
        }
        result.setPower(generateRandomPower(minPower, maxPower));
        pokemonPowerCache.put(result.getName(), result);
        return result;
    }

    private boolean hasType(Pokemon result, String string) {
        if (result.getTypes()
                  .stream()
                  .map(type -> type.toLowerCase())
                  .toList()
                  .contains(string)) {
            return true;
        }
        return false;
    }
}
