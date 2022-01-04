package com.cinema.tickets.common.config.domain.service.impl;

import com.cinema.tickets.common.config.domain.repository.ClienteRepository;
import com.cinema.tickets.common.config.domain.collection.Cliente;
import com.cinema.tickets.common.config.domain.service.ClienteService;
import com.cinema.tickets.common.config.domain.strategy.ClienteStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ClienteServiceImpl implements ClienteService {

    final Logger log = Logger.getLogger(ClienteServiceImpl.class.getName());

    private final ClienteRepository clienteRepository;

    @Autowired
    ClienteStrategy clienteValidationStrategy;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Cliente save(Cliente cliente) {

        log.info("Salvando novo cliente " + cliente.getNome());

        if (clienteValidationStrategy != null) {
            this.clienteValidationStrategy.validate(cliente);
        }

        return this.clienteRepository.save(cliente);
    }

    @Override
    public Optional<Cliente> findById(String id) {
        log.info("Buscando cliente pelo id");
        this.clienteValidationStrategy.findById(id);
        return this.clienteRepository.findById(id);
    }

    @Override
    public void delete(Cliente cliente) {
        Optional<Cliente> clienteId = this.clienteRepository.findById(cliente.getId());

        log.info("Deletando cliente");

        if (clienteId.isEmpty()) {
            this.clienteValidationStrategy.findById(cliente.getId());
        }

        this.clienteRepository.delete(cliente);
    }

    @Override
    public Cliente update(Cliente cliente) {
        log.info("Atualizando cliente");
        return this.clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> findAll() {
        log.info("Buscando todos os clientes");
        return this.clienteRepository.findAll();
    }

}
