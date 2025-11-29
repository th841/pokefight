package org.th.pokefight.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.th.pokefight.core.PokeFightCoreConfig;
import org.th.pokefight.core.PokeFightProperties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@Import(PokeFightCoreConfig.class)
@EnableConfigurationProperties(PokeFightProperties.class)
public class PokeFightApplication {

    private final ObjectMapper objectMapper;

    @Autowired
    public PokeFightApplication(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(PokeFightApplication.class, args);
    }

    @PostConstruct
    public void configureObjectMapper() {
        // add JavaTimeModule to the already customized ObjectMapper in the PokeFightCoreConfig
        if (!objectMapper.getRegisteredModuleIds()
                         .contains(JavaTimeModule.class.getName())) {
            objectMapper.registerModule(new JavaTimeModule());
        }
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
