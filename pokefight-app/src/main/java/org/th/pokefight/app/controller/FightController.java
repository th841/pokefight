package org.th.pokefight.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.th.pokefight.api.FightWebService;
import org.th.pokefight.api.dto.FightDTO;
import org.th.pokefight.app.mapper.FightMapper;
import org.th.pokefight.core.PokeFightConstants;
import org.th.pokefight.core.service.FightService;

@Controller
public class FightController implements FightWebService {

    private FightService fightService;

    @Autowired
    public FightController(FightService fightService) {
        this.fightService = fightService;
    }

    @Override
    public List<FightDTO> getFights() {
        return FightMapper.toDTO(fightService.getLastFights(PokeFightConstants.LIMIT_FIGHTS));
    }

}
