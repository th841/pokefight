package org.th.pokefight.app.mapper;

import java.util.List;

import org.th.pokefight.api.dto.FightDTO;
import org.th.pokefight.core.model.Fight;

/**
 * Simple mapper to map {@link Fight} to {@link FightDTO} objects // Could have used mapstruct too
 * 
 * @author th
 */
public final class FightMapper {

    private FightMapper() {
        // do not instantiate this class
    }

    public static List<FightDTO> toDTO(List<Fight> fights) {
        return fights.stream()
                     .map(FightMapper::toDTO)
                     .toList();
    }

    static FightDTO toDTO(Fight fight) {
        if (fight == null) {
            return null;
        }
        return new FightDTO(PokemonMapper.toDTO(fight.getFighters()), fight.getTimestamp(),
                            PokemonMapper.toDTO(fight.getWinner()));
    }
}
