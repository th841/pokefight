package org.th.pokefight.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.th.pokefight.api.dto.FightDTO;

public interface FightWebService {

    static final String PATH_FIGHTS = "/fights";

    @GetMapping
    @RequestMapping(PATH_FIGHTS)
    @ResponseBody
    List<FightDTO> getFights();

}
