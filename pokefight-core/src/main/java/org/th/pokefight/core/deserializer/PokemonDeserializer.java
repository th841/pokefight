package org.th.pokefight.core.deserializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.th.pokefight.core.model.Pokemon;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class PokemonDeserializer extends JsonDeserializer<Pokemon> {

    private static final String PROP_TYPES = "types";
    private static final String PROP_NAME = "name";
    private static final String PROP_TYPE = "type";
    private static final String SPRITES = "sprites";
    private static final String IMAGE_URL = "front_default";

    @Override
    public Pokemon deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {

        ObjectMapper mapper = (ObjectMapper) jsonParser.getCodec();
        ObjectNode root = mapper.readTree(jsonParser);

        return Pokemon.builder()
                      .name(readName(root))
                      .types(readTypes(root))
                      .imageUrl(readImageUrl(root))
                      .build();
    }

    private String readImageUrl(ObjectNode root) {
        if (root.has(SPRITES)) {
            if (root.get(SPRITES)
                    .has(IMAGE_URL)) {
                String imageUrl = root.get(SPRITES)
                                      .get(IMAGE_URL)
                                      .asText();
                if (root.get(SPRITES)
                        .has("other")) {
                    if (root.get(SPRITES)
                            .get("other")
                            .has("dream_world")) {
                        String url = root.get(SPRITES)
                                         .get("other")
                                         .get("dream_world")
                                         .get(IMAGE_URL)
                                         .asText();
                        if (url == null || url.isEmpty() || "null".equals(url)) {
                            return imageUrl;
                        } else {
                            return url;
                        }
                    } else {
                        return imageUrl;
                    }
                } else {
                    return imageUrl;
                }
            }
        }
        return null;
    }

    private String readName(ObjectNode root) {
        String name = null;
        if (root.has(PROP_NAME)) {
            name = root.get(PROP_NAME)
                       .asText();
        }
        return name;
    }

    private List<String> readTypes(ObjectNode root) {
        List<String> types = new ArrayList<>();

        if (root.has(PROP_TYPES)) {
            root.get(PROP_TYPES)
                .forEach(type -> types.add(getTypeName(type)));
        }
        return types;
    }

    private String getTypeName(JsonNode type) {
        if (type.has(PROP_TYPE)) {
            if (type.get(PROP_TYPE)
                    .has(PROP_NAME)) {
                return type.get(PROP_TYPE)
                           .get(PROP_NAME)
                           .asText();
            }
        }
        return null;
    }
}
