package org.th.pokefight.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.th.pokefight.api.PokemonWebService;
import org.th.pokefight.api.dto.PokemonDTO;
import org.th.pokefight.app.mapper.PokemonMapper;
import org.th.pokefight.core.service.PokemonService;

@Controller
public class PokemonController implements PokemonWebService {

    private final PokemonService pokemonService;

    @Autowired
    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @Override
    public List<PokemonDTO> getRandomPokemons() {
        return List.of(PokemonMapper.toDTO(pokemonService.getRandomPokemon()),
                       PokemonMapper.toDTO(pokemonService.getRandomPokemon()));
    }
}
