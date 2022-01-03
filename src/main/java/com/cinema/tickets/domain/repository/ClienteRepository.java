package com.cinema.tickets.domain.repository;

import com.cinema.tickets.domain.collection.Cliente;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends MongoRepository<Cliente, String> {

    Boolean existsByEmail(String email);

    Boolean existsByCpf(String cpf);

    Optional<Cliente> findByEmail(String email);
}
