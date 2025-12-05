package org.th.pokefight.core.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.th.pokefight.core.model.Pokemon;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Validated
public interface PokemonService {

    /**
     * Returns a random {@link Pokemon}
     * 
     * @return the random pokemon
     */
    @NotNull
    Pokemon getRandomPokemon();
    
    @NotNull
    Pokemon getRandomPokemon(Integer maxPower);

    /**
     * Reads pokemon data by given name
     * 
     * @param name
     *            the pokemon's name
     * @return {@link Pokemon} if found
     * @throws exception
     *             {@link Exception} in case the pokemon not found by the given name
     */
    @NotNull
    Pokemon find(@NotEmpty String name0);

    /**
     * Remove a pokemon with it's generated power from the cache, use it after a fight to get a new generated power if
     * the pokemon has to fight again
     * 
     * @param name
     *            name of the pokemon
     */
    void removeFromCache(@NotNull List<String> names);
}
