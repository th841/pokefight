package org.th.pokefight.core.service.impl;

import java.util.Random;

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

    @Autowired
    public PokemonServiceImpl(PokeFightProperties pokeFightProperties, RestTemplate restTemplate) {
        this.pokeFightProperties = pokeFightProperties;
        this.restTemplate = restTemplate;
    }

    @Override
    public Pokemon getRandomPokemon() {
        int randomPokemonId = generateRandomPokemonId();
        String url = pokeFightProperties.getPokeApiUrlPrefix() + ID_PLACEHOLDER;
        Pokemon result = restTemplate.getForObject(url, Pokemon.class, randomPokemonId);
        result.setPower(generateRandomPower());
        return result;
    }

    private int generateRandomPower() {
        return new Random().nextInt(PokeFightConstants.POWER_MIN, PokeFightConstants.POWER_MAX);
    }

    private int generateRandomPokemonId() {
        return new Random().nextInt(PokeFightConstants.POKEMON_ID_MIN, PokeFightConstants.POKEMON_ID_MAX);
    }

}
