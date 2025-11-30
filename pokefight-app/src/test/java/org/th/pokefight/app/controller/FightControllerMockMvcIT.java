package org.th.pokefight.app.controller;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.th.pokefight.api.FightWebService;
import org.th.pokefight.api.dto.FightRequestDTO;
import org.th.pokefight.app.controller.FightController;
import org.th.pokefight.core.PokeFightConstants;
import org.th.pokefight.core.model.Fight;
import org.th.pokefight.core.model.Pokemon;
import org.th.pokefight.core.repository.FightRepository;
import org.th.pokefight.core.service.FightService;
import org.th.pokefight.core.service.PokemonService;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(FightController.class)
class FightControllerMockMvcIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FightService fightService;

    @MockBean
    private PokemonService pokemonService;

    @MockBean
    private FightRepository fightRepository;

    @Test
    void testGetFights_returnsJsonArray() throws Exception {
        // given
        Pokemon p1 = Pokemon.builder()
                            .name("Pikachu")
                            .types(List.of("electric"))
                            .power(55)
                            .imageUrl("url")
                            .build();
        Pokemon p2 = Pokemon.builder()
                            .name("Bulbasaur")
                            .types(List.of("grass"))
                            .power(49)
                            .imageUrl("url")
                            .build();

        Fight fight1 = new Fight("id1", Instant.now(), List.of(p1, p2), p1);
        Fight fight2 = new Fight("id2", Instant.now(), List.of(p2, p1), p2);

        when(fightService.getLastFights(PokeFightConstants.LIMIT_FIGHTS)).thenReturn(List.of(fight1, fight2));

        // when / then
        mockMvc.perform(get(FightWebService.PATH_FIGHT))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].fighters[0].name").value("Pikachu"))
               .andExpect(jsonPath("$[1].fighters[1].name").value("Pikachu"));

        verify(fightService).getLastFights(PokeFightConstants.LIMIT_FIGHTS);
    }

    @Test
    void fight_returnsWinner() throws Exception {
        // given
        Pokemon pikachu = Pokemon.builder()
                                 .name("Pikachu")
                                 .types(List.of("electric"))
                                 .power(55)
                                 .imageUrl("url")
                                 .build();
        Pokemon charmander = Pokemon.builder()
                                    .name("Charmander")
                                    .types(List.of("fire"))
                                    .power(52)
                                    .imageUrl("url")
                                    .build();

        Fight fight = Fight.builder()
                           .fighters(List.of(pikachu, charmander))
                           .timestamp(Instant.now())
                           .winner(pikachu)
                           .build();

        when(pokemonService.find("Pikachu")).thenReturn(pikachu);
        when(pokemonService.find("Charmander")).thenReturn(charmander);
        when(fightService.fight(List.of(pikachu, charmander))).thenReturn(fight);

        FightRequestDTO fightRequest = new FightRequestDTO(List.of("Pikachu", "Charmander"));
        String json = new ObjectMapper().writeValueAsString(fightRequest);

        // when / then
        mockMvc.perform(post(FightWebService.PATH_FIGHT).contentType(MediaType.APPLICATION_JSON)
                                                        .content(json))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.name").value("Pikachu"))
               .andExpect(jsonPath("$.types[0]").value("electric"))
               .andExpect(jsonPath("$.power").value(55));

        verify(pokemonService).find("Pikachu");
        verify(pokemonService).find("Charmander");
        verify(fightService).fight(List.of(pikachu, charmander));
    }
}
