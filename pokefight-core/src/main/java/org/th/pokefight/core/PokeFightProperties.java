package org.th.pokefight.core;

import org.springframework.boot.context.properties.ConfigurationProperties;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@ConfigurationProperties("pokefight")
@Data
public class PokeFightProperties {

    @NotEmpty
    private String pokeApiUrlPrefix;

}
