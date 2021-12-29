package com.cinema.tickets.domain.service.impl;


import com.cinema.tickets.domain.collection.Cliente;
import com.cinema.tickets.domain.repository.ClienteRepository;
import com.cinema.tickets.domain.service.ClienteService;
import com.cinema.tickets.domain.strategy.ClienteStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    @Autowired
    ClienteStrategy clienteValidationStrategy;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente save(Cliente cliente) {

        if (clienteValidationStrategy != null) {
            this.clienteValidationStrategy.validate(cliente);
        }

        return this.clienteRepository.save(cliente);
    }

    @Override
    public Optional<Cliente> findById(String id) {
        this.clienteValidationStrategy.findById(id);
        return this.clienteRepository.findById(id);
    }

    @Override
    public void delete(Cliente cliente) {
        Optional<Cliente> clienteId = this.clienteRepository.findById(cliente.getId());

        if (clienteId.isEmpty()) {
            this.clienteValidationStrategy.findById(cliente.getId());
        }

        this.clienteRepository.delete(cliente);
    }

    @Override
    public Cliente update(Cliente cliente) {
        return this.clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> findAll() {
        return this.clienteRepository.findAll();
    }
}
