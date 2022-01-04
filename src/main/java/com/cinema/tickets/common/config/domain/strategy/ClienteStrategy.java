package com.cinema.tickets.common.config.domain.strategy;

import com.cinema.tickets.common.config.domain.collection.Cliente;

public interface ClienteStrategy {

    void validate(Cliente cliente);

    void findById(String id);
}
