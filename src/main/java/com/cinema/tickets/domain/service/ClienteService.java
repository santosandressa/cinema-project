package com.cinema.tickets.domain.service;

import com.cinema.tickets.domain.collection.Cliente;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    Cliente save(Cliente cliente);

    Optional<Cliente> findById(String id);

    void delete(Cliente cliente);

    Cliente update(Cliente cliente);

    List<Cliente> findAll();
}
