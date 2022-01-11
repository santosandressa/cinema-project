package com.cinema.tickets.domain.service;

import com.cinema.tickets.domain.collection.Cliente;
import com.cinema.tickets.domain.collection.Role;

import java.util.List;
import java.util.Optional;

public interface ClienteService {

    Cliente save(Cliente cliente);

    Optional<Cliente> findById(String id);

    void delete(Cliente cliente);

    Cliente update(Cliente cliente);

    List<Cliente> findAll();

    Optional<Cliente> findClienteByEmail(String email);

    Role saveRole(Role role);

    void addRole(String email, String nomeRole);

    Role findRoleByNome(String nomeRole);
}
