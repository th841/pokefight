package org.th.pokefight.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.th.pokefight.api.dto.PokemonDTO;

public interface PokemonWebService {

    static final String PATHVAR_NAME0 = "/{name0}";
    static final String PATHVAR_NAME1 = "/{name1}";

    static final String PATH_POKEMONS = "/pokemons";
    static final String PATH_WINNER = "/winner";
    static final String PATH_RANDOM = "/random";

    static final String PATH_POKEMON_RANDOM = PATH_POKEMONS + PATH_RANDOM;
    static final String PATH_WINNER_NAME0_NAME1 = PATH_POKEMONS + PATH_WINNER + PATHVAR_NAME0 + PATHVAR_NAME1;

    @GetMapping(PATH_POKEMON_RANDOM)
    List<PokemonDTO> getRandomPokemons();

    @GetMapping(PATH_WINNER_NAME0_NAME1)
    PokemonDTO getWinner(@PathVariable String name0, @PathVariable String name1);

}
