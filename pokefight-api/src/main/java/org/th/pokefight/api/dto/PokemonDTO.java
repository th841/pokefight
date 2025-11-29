package org.th.pokefight.api.dto;

import java.util.List;

public record PokemonDTO(String name, List<String> types, Integer power, String imageUrl) {

}
