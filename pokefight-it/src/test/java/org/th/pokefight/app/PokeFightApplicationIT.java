package org.th.pokefight.app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PokeFightApplication.class)
public class PokeFightApplicationIT {

    @Test
    void contextLoads() {
        // checks that the Spring Boot context is loaded without exception
        assertTrue(true, "Context did not load");
    }
}
