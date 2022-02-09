package com.cinema.tickets;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ImportAutoConfiguration( FeignAutoConfiguration.class)
@ActiveProfiles("test")
class TicketsApplicationTests {

    @Test
    void contextLoads() {
    }


}
