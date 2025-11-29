package org.th.pokefight.app.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.th.pokefight.api.dto.PokemonDTO;
import org.th.pokefight.core.model.Pokemon;

class PokemonMapperTest {

    @Test
    void toDTO_singlePokemon() {
        // given
        Pokemon pokemon = Pokemon.builder()
                                 .name("Pikachu")
                                 .types(List.of("electric"))
                                 .power(55)
                                 .imageUrl("https://img/pikachu.png")
                                 .build();
        // when
        PokemonDTO dto = PokemonMapper.toDTO(pokemon);

        // then
        assertNotNull(dto);
        assertEquals(dto.name(), "Pikachu");
        assertTrue(dto.types()
                      .contains("electric"));
        assertEquals(dto.types()
                        .size(),
                     1);
        assertEquals(dto.power(), 55);
        assertEquals(dto.imageUrl(), "https://img/pikachu.png");
    }

    @Test
    void toDTO_nullPokemon() {
        // when
        PokemonDTO dto = PokemonMapper.toDTO((Pokemon) null);

        // then
        assertNull(dto);
    }

    @Test
    void toDTO_list() {
        // given
        Pokemon p1 = Pokemon.builder()
                            .name("Pikachu")
                            .types(List.of("electric"))
                            .power(55)
                            .imageUrl("url1")
                            .build();
        Pokemon p2 = Pokemon.builder()
                            .name("Bulbasaur")
                            .types(List.of("grass", "poison"))
                            .power(49)
                            .imageUrl("url2")
                            .build();

        // when
        List<PokemonDTO> dtos = PokemonMapper.toDTO(List.of(p1, p2));

        // then
        assertEquals(dtos.size(), 2);

        assertEquals(dtos.get(0)
                         .name(),
                     "Pikachu");
        assertEquals(dtos.get(1)
                         .name(),
                     "Bulbasaur");
    }
}
