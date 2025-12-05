package org.th.pokefight.api;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.th.pokefight.api.dto.MaxPowerRequestDTO;
import org.th.pokefight.api.dto.PokemonDTO;

@RequestMapping(PokemonWebService.PATH_POKEMONS)
@ResponseBody
public interface PokemonWebService {

    static final String PATH_POKEMONS = "/pokemons";
    static final String PATH_RANDOM = "/random";

    @GetMapping(PATH_RANDOM)
    List<PokemonDTO> getRandomPokemons();

    @PostMapping(PATH_RANDOM)
    List<PokemonDTO> getRandomPokemons(@RequestBody MaxPowerRequestDTO maxPowerDTO);
}
