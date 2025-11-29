package org.th.pokefight.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.th.pokefight.api.FightWebService;
import org.th.pokefight.api.dto.FightDTO;
import org.th.pokefight.api.dto.FightRequestDTO;
import org.th.pokefight.api.dto.PokemonDTO;
import org.th.pokefight.app.mapper.FightMapper;
import org.th.pokefight.app.mapper.PokemonMapper;
import org.th.pokefight.core.PokeFightConstants;
import org.th.pokefight.core.model.Fight;
import org.th.pokefight.core.model.Pokemon;
import org.th.pokefight.core.service.FightService;
import org.th.pokefight.core.service.PokemonService;

@Controller
public class FightController implements FightWebService {

    private final FightService fightService;
    private final PokemonService pokemonService;

    @Autowired
    public FightController(FightService fightService, PokemonService pokemonService) {
        this.fightService = fightService;
        this.pokemonService = pokemonService;
    }

    @Override
    public List<FightDTO> getFights() {
        return FightMapper.toDTO(fightService.getLastFights(PokeFightConstants.LIMIT_FIGHTS));
    }

    @Override
    public PokemonDTO fight(FightRequestDTO fightRequest) {
        List<Pokemon> pokemons = fightRequest.names()
                                             .stream()
                                             .map(pokemonService::find)
                                             .toList();
        Fight fight = fightService.fight(pokemons);
        return PokemonMapper.toDTO(fight.getWinner());
    }

}
