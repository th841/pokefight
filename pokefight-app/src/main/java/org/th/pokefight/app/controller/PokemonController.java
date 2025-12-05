package org.th.pokefight.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.th.pokefight.api.PokemonWebService;
import org.th.pokefight.api.dto.MaxPowerRequestDTO;
import org.th.pokefight.api.dto.PokemonDTO;
import org.th.pokefight.app.mapper.PokemonMapper;
import org.th.pokefight.core.service.PokemonService;

@Controller
@CrossOrigin(origins = "http://localhost:4200")
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

    @Override
    public List<PokemonDTO> getRandomPokemons(MaxPowerRequestDTO maxPowerDTO) {
        return List.of(PokemonMapper.toDTO(pokemonService.getRandomPokemon(maxPowerDTO.max0())),
                       PokemonMapper.toDTO(pokemonService.getRandomPokemon(maxPowerDTO.max1())));
    }
}
