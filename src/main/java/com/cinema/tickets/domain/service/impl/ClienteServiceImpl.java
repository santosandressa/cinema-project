package com.cinema.tickets.domain.service.impl;


import com.cinema.tickets.domain.collection.Cliente;
import com.cinema.tickets.domain.repository.ClienteRepository;
import com.cinema.tickets.domain.service.ClienteService;
import com.cinema.tickets.domain.service.strategy.ClienteStrategyImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteStrategyImpl clienteValidationStrategy;

    @Override
    public Cliente save(Cliente cliente) {
        this.clienteValidationStrategy.validate(cliente);
        return clienteRepository.save(cliente);
    }

    @Override
    public Optional<Cliente> findById(String id) {
        return this.clienteRepository.findById(id);
    }

    @Override
    public void delete(Cliente cliente) {
        this.clienteRepository.delete(cliente);
    }

    @Override
    public Cliente update(Cliente cliente) {
        return this.clienteRepository.save(cliente);
    }
}
