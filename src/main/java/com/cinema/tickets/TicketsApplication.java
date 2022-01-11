package com.cinema.tickets;

import com.cinema.tickets.domain.collection.Cliente;
import com.cinema.tickets.domain.collection.Endereco;
import com.cinema.tickets.domain.collection.Role;
import com.cinema.tickets.domain.service.ClienteService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;


@EnableMongoAuditing
@ComponentScan({"com.cinema.tickets", "com.cinema.tickets.common.config"})
@EnableMongoRepositories("com.cinema.tickets.domain.repository")
@SpringBootApplication
public class TicketsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketsApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    CommandLineRunner run(ClienteService clienteService) {
//        return args -> {
//            clienteService.saveRole(new Role(null, "ROLE_ADMIN"));
//            clienteService.saveRole(new Role(null, "ROLE_USER"));
//
//            Cliente cliente = new Cliente();
//            cliente.setNome("Helena Rayssa Daniela Rocha");
//            cliente.setCpf("940.757.328-19");
//            cliente.setDataNascimento("06/01/1975");
//            cliente.setCelular("(98) 99639-0465");
//            cliente.setEmail("helenarayssadanielarocha-83@microlasersp.com.br");
//            cliente.setSenha("IBz7IShI9u");
//
//            Endereco endereco = new Endereco();
//            endereco.setRua("Rua Onze");
//            endereco.setCep("65058-743");
//            endereco.setNumero("964");
//            endereco.setComplemento("Apto. 101");
//            endereco.setBairro("Santa Efigênia");
//            endereco.setCidade("São Luís");
//            endereco.setEstado("MA");
//
//            cliente.setEndereco(endereco);
//            cliente.setRoles(new ArrayList<>());
//
//            clienteService.save(cliente);
//
//            clienteService.addRole("helenarayssadanielarocha-83@microlasersp.com.br", "ROLE_ADMIN");
//    };
//
//}
}
