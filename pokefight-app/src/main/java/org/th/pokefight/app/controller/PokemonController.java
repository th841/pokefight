package org.th.pokefight.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.th.pokefight.api.PokemonWebService;
import org.th.pokefight.api.dto.PokemonDTO;
import org.th.pokefight.app.mapper.PokemonMapper;
import org.th.pokefight.core.model.Pokemon;
import org.th.pokefight.core.service.FightService;
import org.th.pokefight.core.service.PokemonService;

@Controller
public class PokemonController implements PokemonWebService {

    private final PokemonService pokemonService;
    private final FightService fightService;

    @Autowired
    public PokemonController(PokemonService pokemonService, FightService fightService) {
        this.pokemonService = pokemonService;
        this.fightService = fightService;
    }

    @Override
    public List<PokemonDTO> fightRandomPokemons() {
        Pokemon pokemon0 = pokemonService.getRandomPokemon();
        Pokemon pokemon1 = pokemonService.getRandomPokemon();

        fightService.fight(pokemon0, pokemon1);

        PokemonDTO pokemon0DTO = PokemonMapper.toDTO(pokemon0);
        PokemonDTO pokemon1DTO = PokemonMapper.toDTO(pokemon1);
        return List.of(pokemon0DTO, pokemon1DTO);
    }

    @Override
    public PokemonDTO getWinner(String name0, String name1) {
        // TODO Auto-generated method stub
        return null;
    }

}
