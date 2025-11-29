package org.th.pokefight.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.th.pokefight.api.FightWebService;
import org.th.pokefight.api.dto.FightDTO;
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
    public PokemonDTO fight(String name0, String name1) {
        Pokemon pokemon0 = pokemonService.find(name0);
        Pokemon pokemon1 = pokemonService.find(name1);
        Fight fight = fightService.fight(pokemon0, pokemon1);
        return PokemonMapper.toDTO(fight.getWinner());
    }

}
