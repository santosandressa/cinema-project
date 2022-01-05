package com.cinema.tickets;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongoAuditing
@ComponentScan({"com.cinema.tickets", "com.cinema.tickets.common.config"})
@EnableMongoRepositories("com.cinema.tickets.domain.repository")
@SpringBootApplication

public class TicketsApplication  {

    public static void main(String[] args) {
        SpringApplication.run(TicketsApplication.class, args);
    }

}
