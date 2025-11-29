package org.th.pokefight.api;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.th.pokefight.api.dto.FightDTO;
import org.th.pokefight.api.dto.FightRequestDTO;
import org.th.pokefight.api.dto.PokemonDTO;

@ResponseBody
public interface FightWebService {

    static final String PATH_FIGHT = "/fight";

    @GetMapping
    @RequestMapping(PATH_FIGHT)
    List<FightDTO> getFights();

    @PostMapping(PATH_FIGHT)
    PokemonDTO fight(@RequestBody FightRequestDTO fightRequest);
}
