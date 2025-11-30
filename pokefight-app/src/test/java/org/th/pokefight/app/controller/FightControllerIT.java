package org.th.pokefight.app.controller;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.th.pokefight.api.dto.FightDTO;
import org.th.pokefight.api.dto.FightRequestDTO;
import org.th.pokefight.api.dto.PokemonDTO;
import org.th.pokefight.app.controller.FightController;
import org.th.pokefight.app.mapper.PokemonMapper;
import org.th.pokefight.core.PokeFightConstants;
import org.th.pokefight.core.model.Fight;
import org.th.pokefight.core.model.Pokemon;
import org.th.pokefight.core.service.FightService;
import org.th.pokefight.core.service.PokemonService;

@SpringBootTest
class FightControllerIT {

    @Autowired
    private FightController fightController;

    @MockBean
    private FightService fightService;

    @MockBean
    private PokemonService pokemonService;

    @Test
    void testGetFights_returnsMappedFights() {
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

        // when
        List<FightDTO> fights = fightController.getFights();

        // then
        assertEquals(fights.size(), 2);
        assertEquals(fights.get(0)
                           .fighters(),
                     PokemonMapper.toDTO(List.of(p1, p2)));
        assertNotNull(fights.get(1)
                            .fighters());

        verify(fightService).getLastFights(PokeFightConstants.LIMIT_FIGHTS);
    }

    @Test
    void testFight_returnsWinnerMapped() {
        // given
        Pokemon pikachu = new Pokemon("Pikachu", List.of("electric"), "url1", 55);
        Pokemon charmander = new Pokemon("Charmander", List.of("fire"), "url2", 52);

        Fight fight = new Fight("id1", Instant.now(), List.of(pikachu, charmander), pikachu);

        when(pokemonService.find("Pikachu")).thenReturn(pikachu);
        when(pokemonService.find("Charmander")).thenReturn(charmander);
        when(fightService.fight(List.of(pikachu, charmander))).thenReturn(fight);

        // when
        PokemonDTO winner = fightController.fight(new FightRequestDTO(List.of("Pikachu", "Charmander")));

        // then
        assertEquals(winner.name(), "Pikachu");
        assertTrue(winner.types()
                         .contains("electric"));
        assertEquals(winner.types()
                           .size(),
                     1);
        assertEquals(winner.power(), 55);

        verify(pokemonService).find("Pikachu");
        verify(pokemonService).find("Charmander");
        verify(fightService).fight(List.of(pikachu, charmander));
    }
}
