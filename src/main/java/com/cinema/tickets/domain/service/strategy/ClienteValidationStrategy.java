package com.cinema.tickets.domain.service.strategy;

import com.cinema.tickets.domain.collection.Cliente;
import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteValidationStrategy implements ClienteStrategy{

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void validate(Cliente cliente) {
        Boolean emailExists = clienteRepository.existsByEmail(cliente.getEmail());

        if(emailExists){
            throw new BusinessException("Email já cadastrado");
        }
    }
}
