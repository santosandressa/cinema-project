package com.cinema.tickets.domain.service.strategy;

import com.cinema.tickets.domain.collection.Cliente;

public interface ClienteStrategy {

    void validate(Cliente cliente);
}
