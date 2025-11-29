package org.th.pokefight.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.th.pokefight.core.deserializer.PokemonDeserializer;
import org.th.pokefight.core.model.Pokemon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Configuration
@ComponentScan
@EnableMongoRepositories
public class PokeFightCoreConfig {

    /**
     * Custom {@link RestTemplate} that uses the custom {@link ObjectMapper} bean
     * 
     * @param objectMapper
     *            the ObjectMapper to be used
     * @return the customized {@link RestTemplate} bean
     */
    @Bean
    public RestTemplate restTemplate(ObjectMapper objectMapper) {
        RestTemplate restTemplate = new RestTemplate();

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(objectMapper);

        restTemplate.getMessageConverters()
                    .add(0, converter);

        return restTemplate;
    }

    /**
     * Register the custom Pokemon deserializer on the {@link ObjectMapper} bean
     * 
     * @return customized {@link ObjectMapper}
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Pokemon.class, new PokemonDeserializer());
        mapper.registerModule(module);

        return mapper;
    }
}
