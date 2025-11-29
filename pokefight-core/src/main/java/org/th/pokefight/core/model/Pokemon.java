package org.th.pokefight.core.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public final class Pokemon {

    private String name;
    private List<String> types;
    private String imageUrl;
    private Integer power;

}
