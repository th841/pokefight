package org.th.pokefight.app.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.th.pokefight.api.PokemonWebService;
import org.th.pokefight.core.model.Pokemon;
import org.th.pokefight.core.repository.FightRepository;
import org.th.pokefight.core.service.PokemonService;

@WebMvcTest(PokemonController.class)
class PokemonControllerMockMvcIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PokemonService pokemonService;

    @MockBean
    private FightRepository fightRepository;

    @Test
    void getRandomPokemons() throws Exception {
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

        when(pokemonService.getRandomPokemon()).thenReturn(p1, p2);

        mockMvc.perform(get(PokemonWebService.PATH_POKEMONS
                + PokemonWebService.PATH_RANDOM).contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].name").value("Pikachu"))
               .andExpect(jsonPath("$[0].power").value(55))
               .andExpect(jsonPath("$[1].name").value("Bulbasaur"))
               .andExpect(jsonPath("$[1].types[0]").value("grass"))
               .andExpect(jsonPath("$[1].power").value(49));
    }
}
