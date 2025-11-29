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
import org.th.pokefight.core.model.Pokemon;
import org.th.pokefight.core.service.PokemonService;

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
        Pokemon result = getPokemonWithRandomPower(String.valueOf(randomPokemonId));
        pokemonPowerCache.put(result.getName(), result);
        return result;
    }

    private int generateRandomPower() {
        return new Random().nextInt(PokeFightConstants.POWER_MIN, PokeFightConstants.POWER_MAX);
    }

    private int generateRandomPokemonId() {
        return new Random().nextInt(PokeFightConstants.POKEMON_ID_MIN, PokeFightConstants.POKEMON_ID_MAX);
    }

    @Override
    public Pokemon find(String name) {
        return pokemonPowerCache.computeIfAbsent(name, this::getPokemonWithRandomPower);
    }

    @Override
    public void removeFromCache(List<String> names) {
        names.forEach(pokemonPowerCache::remove);
    }

    /** Lucky that we can use id OR name to query the poke API */
    private Pokemon getPokemonWithRandomPower(String nameOrId) {
        String url = pokeFightProperties.getPokeApiUrlPrefix() + ID_PLACEHOLDER;
        Pokemon result = restTemplate.getForObject(url, Pokemon.class, nameOrId);
        if (result == null) {
            // cannot be null, exception thrown in restTemplate.getForObject call above
        }
        result.setPower(generateRandomPower());
        return result;
    }
}
