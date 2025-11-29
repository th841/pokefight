package org.th.pokefight.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.th.pokefight.api.dto.FightDTO;
import org.th.pokefight.api.dto.PokemonDTO;

@ResponseBody
public interface FightWebService {

    static final String PATHVAR_NAME0 = "name0";
    static final String PATHVAR_NAME1 = "name1";

    static final String PATH_FIGHT = "/fight";

    static final String PATH_FIGHT_NAME0_NAME1 = PATH_FIGHT + "/{" + PATHVAR_NAME0 + "}" + "/{" + PATHVAR_NAME1 + "}";

    @GetMapping
    @RequestMapping(PATH_FIGHT)
    List<FightDTO> getFights();

    @GetMapping(PATH_FIGHT_NAME0_NAME1)
    PokemonDTO fight(@PathVariable String name0, @PathVariable String name1);
}
