package com.cinema.tickets.domain.strategy.impl;

import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.exception.NotFoundException;
import com.cinema.tickets.domain.repository.ClienteRepository;
import com.cinema.tickets.domain.strategy.ClienteStrategy;
import com.cinema.tickets.domain.collection.Cliente;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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

