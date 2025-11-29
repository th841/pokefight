package org.th.pokefight.core.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;
import org.th.pokefight.core.PokeFightProperties;
import org.th.pokefight.core.model.Pokemon;

class PokemonServiceImplTest {

    @Mock
    private PokeFightProperties properties;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PokemonServiceImpl pokemonService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        when(properties.getPokeApiUrlPrefix()).thenReturn("https://pokeapi.co/api/v2/pokemon/");
    }

    @Test
    void getRandomPokemon_returnsPokemonAndCachesIt() {
        Pokemon apiPokemon = Pokemon.builder()
                                    .name("Pikachu")
                                    .types(List.of("electric"))
                                    .power(55)
                                    .imageUrl("url")
                                    .build();
        when(restTemplate.getForObject(anyString(), eq(Pokemon.class), anyString())).thenReturn(apiPokemon);

        Pokemon result = pokemonService.getRandomPokemon();

        assertNotNull(result, "pokemon should not be null");
        assertEquals("Pikachu", result.getName());
        assertTrue(result.getPower() >= 0);

        // cache
        Pokemon cached = pokemonService.find("Pikachu");
        assertSame(result, cached);
    }

    @Test
    void find_returnsFromCacheIfExists() {
        Pokemon cachedPokemon = Pokemon.builder()
                                       .name("Bulbasaur")
                                       .types(List.of("grass"))
                                       .power(50)
                                       .imageUrl("url2")
                                       .build();
        when(restTemplate.getForObject(anyString(), eq(Pokemon.class), anyString())).thenReturn(cachedPokemon);

        // manually populate cache
        pokemonService.getRandomPokemon();

        when(restTemplate.getForObject(anyString(), eq(Pokemon.class), eq("Bulbasaur"))).thenReturn(cachedPokemon);
        pokemonService.find("Bulbasaur");

        Pokemon result1 = pokemonService.find("Bulbasaur");
        Pokemon result2 = pokemonService.find("Bulbasaur");

        assertSame(result1, result2, "should return the same cached instance");
    }

    @Test
    void removeFromCache_removesEntries() {
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

        when(restTemplate.getForObject(anyString(), eq(Pokemon.class), anyString())).thenReturn(p1, p2);

        Pokemon r1 = pokemonService.getRandomPokemon();
        pokemonService.getRandomPokemon();

        pokemonService.removeFromCache(List.of("Pikachu"));

        Pokemon afterRemoval = pokemonService.find("Pikachu");
        assertNotSame(r1, afterRemoval, "Removed Pokemon should be fetched again (not cached)");
    }

}
