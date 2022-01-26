package com.cinema.tickets.domain.strategy.impl;

import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.exception.NotFoundException;
import com.cinema.tickets.domain.repository.ClienteRepository;
import com.cinema.tickets.domain.strategy.ClienteStrategy;
import com.cinema.tickets.domain.collection.Cliente;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.logging.Logger;

@Component
public class ClienteStrategyImpl implements ClienteStrategy {

    private final ClienteRepository clienteRepository;

    final Logger log = Logger.getLogger(ClienteStrategyImpl.class.getName());

    public ClienteStrategyImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public void validate(Cliente cliente) {

        Boolean emailExists = this.clienteRepository.existsByEmail(cliente.getEmail());

        if (emailExists) {
            log.info("Email já cadastrado");
            throw new BusinessException("Email já cadastrado");
        }

        Boolean cpfExists = this.clienteRepository.existsByCpf(cliente.getCpf());

        if (cpfExists) {
            log.info("CPF já cadastrado");
            throw new BusinessException("CPF já cadastrado");
        }

        if (cliente.getEndereco() == null) {
            log.info("Endereço não informado");
            throw new BusinessException("Endereço não informado");
        }

        log.info("Cliente validado");
    }

    @Override
    public void findById(String id) {

        Optional<Cliente> cliente = this.clienteRepository.findById(id);

        if (cliente.isEmpty()) {
            log.info("Cliente não encontrado");
            throw new NotFoundException("Cliente não encontrado");
        }

        log.info("Cliente encontrado");
    }


}

