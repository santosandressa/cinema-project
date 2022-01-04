package com.cinema.tickets.common.config.domain.strategy.impl;

import com.cinema.tickets.common.config.domain.exception.BusinessException;
import com.cinema.tickets.common.config.domain.exception.NotFoundException;
import com.cinema.tickets.common.config.domain.repository.ClienteRepository;
import com.cinema.tickets.common.config.domain.strategy.ClienteStrategy;
import com.cinema.tickets.common.config.domain.collection.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ClienteStrategyImpl implements ClienteStrategy {

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public void validate(Cliente cliente) {

        Boolean emailExists = this.clienteRepository.existsByEmail(cliente.getEmail());

        if (emailExists) {
            throw new BusinessException("Email já cadastrado");
        }

        Boolean cpfExists = this.clienteRepository.existsByCpf(cliente.getCpf());

        if (cpfExists) {
            throw new BusinessException("CPF já cadastrado");
        }

        if (cliente.getEndereco() == null) {
            throw new BusinessException("Endereço não informado");
        }
    }

    @Override
    public void findById(String id) {

        Optional<Cliente> cliente = this.clienteRepository.findById(id);

        if (cliente.isEmpty()) {
            throw new NotFoundException("Cliente não encontrado");
        }
    }

}

