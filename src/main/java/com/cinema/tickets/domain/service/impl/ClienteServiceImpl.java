package com.cinema.tickets.domain.service.impl;


import com.cinema.tickets.domain.collection.Cliente;
import com.cinema.tickets.domain.repository.ClienteRepository;
import com.cinema.tickets.domain.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
