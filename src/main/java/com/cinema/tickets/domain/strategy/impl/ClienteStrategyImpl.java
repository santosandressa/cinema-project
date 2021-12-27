package com.cinema.tickets.domain.strategy.impl;

import com.cinema.tickets.domain.collection.Cliente;
import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.exception.NotFoundException;
import com.cinema.tickets.domain.repository.ClienteRepository;
import com.cinema.tickets.domain.strategy.ClienteStrategy;
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
    }

    @Override
    public void findById(String id) {

        Optional<Cliente> cliente = this.clienteRepository.findById(id);

        if (cliente.isEmpty()) {
            throw new NotFoundException("Cliente não encontrado");
        }
    }

}
