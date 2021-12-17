package com.cinema.tickets.domain.repository;

import com.cinema.tickets.domain.collection.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String> {

    Boolean existsByEmail(String email);

    Boolean existsByCpf(String cpf);
}
