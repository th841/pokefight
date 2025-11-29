package org.th.pokefight.app.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.th.pokefight.api.dto.FightDTO;
import org.th.pokefight.api.dto.PokemonDTO;
import org.th.pokefight.core.model.Fight;
import org.th.pokefight.core.model.Pokemon;

class FightMapperTest {

    @Test
    void toDTO_singleFight() {
        // given
        Pokemon fighter1 = Pokemon.builder()
                                  .name("Pikachu")
                                  .build();
        Pokemon fighter2 = Pokemon.builder()
                                  .name("Bulbasaur")
                                  .build();
        List<Pokemon> fighters = List.of(fighter1, fighter2);

        Pokemon winner = fighter2;
        Instant timestamp = Instant.now();

        Fight fight = Fight.builder()
                           .fighters(fighters)
                           .timestamp(timestamp)
                           .winner(winner)
                           .build();

        PokemonDTO fighter1DTO = new PokemonDTO("Pikachu", null, null, null);
        PokemonDTO winnerDTO = new PokemonDTO("Bulbasaur", null, null, null);

        List<PokemonDTO> fightersDTO = List.of(fighter1DTO, winnerDTO);

        // when
        FightDTO dto = FightMapper.toDTO(fight);

        // then
        assertNotNull(dto);
        assertEquals(dto.fighters(), fightersDTO);
        assertEquals(dto.timestamp(), timestamp);
        assertEquals(dto.winner(), winnerDTO);
    }

    @Test
    void toDTO_returnsNullOnNullInput() {
        assertNull(FightMapper.toDTO((Fight) null));
    }

    @Test
    void toDTO_list_returnsNullOnNullInput() {
        assertNull(FightMapper.toDTO((List<Fight>) null));
    }

    @Test
    void toDTO_listMapping() {
        // given
        Fight f1 = new Fight("id1", Instant.now(), List.of(), null);
        Fight f2 = new Fight("id2", Instant.now(), List.of(), null);

        // when
        List<FightDTO> dtos = FightMapper.toDTO(List.of(f1, f2));

        // then
        assertEquals(dtos.size(), 2);
        assertEquals(dtos.get(0)
                         .timestamp(),
                     f1.getTimestamp());
        assertEquals(dtos.get(1)
                         .timestamp(),
                     f2.getTimestamp());
    }
}
