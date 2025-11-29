package org.th.pokefight.app.mapper;

import java.util.List;

import org.th.pokefight.api.dto.PokemonDTO;
import org.th.pokefight.core.model.Pokemon;

/**
 * Simple mapper to convert {@link Pokemon} to {@link PokemonDTO}, Could have used mapstruct too
 */
public final class PokemonMapper {

    private PokemonMapper() {
        // for static methods now, do not instantiate
    }

    /**
     * Convert a {@link Pokemon} model object to {@link PokemonDTO}
     * 
     * @param pokemon
     *            the pokemon to be converted
     * @return the DTO object describing the pokemon model
     */
    public static PokemonDTO toDTO(Pokemon pokemon) {
        if (pokemon == null) {
            return null;
        }
        return new PokemonDTO(pokemon.getName(), pokemon.getTypes(), pokemon.getPower(), pokemon.getImageUrl());
    }

    /**
     * Convert a list of {@link Pokemon} model objects to List of {@link PokemonDTO}
     * 
     * @param pokemons
     *            list of pokemons to be converted
     * @return the DTO object list describing the pokemon models
     */
    public static List<PokemonDTO> toDTO(List<Pokemon> pokemons) {
        return pokemons.stream()
                       .map(PokemonMapper::toDTO)
                       .toList();
    }
}
