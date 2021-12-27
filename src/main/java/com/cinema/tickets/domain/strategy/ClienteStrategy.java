package com.cinema.tickets.domain.strategy;

import com.cinema.tickets.domain.collection.Cliente;

public interface ClienteStrategy {

    void validate(Cliente cliente);

    void findById(String id);
}
