package org.th.pokefight.api.dto;

import java.time.Instant;
import java.util.List;

public record FightDTO(List<PokemonDTO> fighters, Instant timestamp, PokemonDTO winner) {

}
