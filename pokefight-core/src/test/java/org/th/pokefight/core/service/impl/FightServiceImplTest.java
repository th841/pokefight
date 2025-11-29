package org.th.pokefight.core.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.th.pokefight.core.model.Fight;
import org.th.pokefight.core.model.Pokemon;
import org.th.pokefight.core.repository.FightRepository;
import org.th.pokefight.core.service.PokemonService;

class FightServiceImplTest {

    @Mock
    private FightRepository fightRepository;

    @Mock
    private PokemonService pokemonService;

    @InjectMocks
    private FightServiceImpl fightService;

    @Captor
    private ArgumentCaptor<Fight> fightCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getLastFights_returnsFightsFromRepository() {
        // given
        Fight f1 = Fight.builder()
                        .timestamp(Instant.now())
                        .build();
        Fight f2 = Fight.builder()
                        .timestamp(Instant.now()
                                          .minusSeconds(10))
                        .build();
        Page<Fight> page = new PageImpl<>(List.of(f1, f2));

        when(fightRepository.findAll(any(Pageable.class))).thenReturn(page);

        // when
        List<Fight> result = fightService.getLastFights(5);

        // then
        assertEquals(2, result.size(), "Should return 2 fights");
        assertSame(f1, result.get(0), "First fight should match");
        assertSame(f2, result.get(1), "Second fight should match");
        verify(fightRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void fight_persistsFightAndRemovesFromCache() {
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
        List<Pokemon> fighters = List.of(p1, p2);

        // when
        Fight result = fightService.fight(fighters);

        // then
        assertSame(p1, result.getWinner());

        // verify fight save
        verify(fightRepository).save(fightCaptor.capture());
        Fight savedFight = fightCaptor.getValue();
        assertSame(p1, savedFight.getWinner());
        assertEquals(fighters, savedFight.getFighters());

        // verify pokemonService.removeFromCache called with names
        verify(pokemonService).removeFromCache(List.of("Pikachu", "Bulbasaur"));
    }

    @Test
    void fight_emptyList_returnsFightWithNoWinner() {
        List<Pokemon> fighters = List.of();

        Fight result = fightService.fight(fighters);

        assertNull(result.getWinner(), "Winner should be null for empty fight");
        assertTrue(result.getFighters()
                         .isEmpty(),
                   "Fighters list should be empty");

        verify(fightRepository).save(any(Fight.class));
        verify(pokemonService).removeFromCache(List.of());
    }

}
