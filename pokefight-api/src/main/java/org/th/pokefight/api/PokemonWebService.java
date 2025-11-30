package org.th.pokefight.api;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.th.pokefight.api.dto.PokemonDTO;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(PokemonWebService.PATH_POKEMONS)
@ResponseBody
public interface PokemonWebService {

    static final String PATH_POKEMONS = "/pokemons";
    static final String PATH_RANDOM = "/random";

    @GetMapping(PATH_RANDOM)
    List<PokemonDTO> getRandomPokemons();
}
