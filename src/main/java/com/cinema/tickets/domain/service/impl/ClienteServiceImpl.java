package com.cinema.tickets.domain.service.impl;

import com.cinema.tickets.domain.collection.Cliente;
import com.cinema.tickets.domain.collection.Role;
import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.repository.ClienteRepository;
import com.cinema.tickets.domain.repository.RoleRepository;
import com.cinema.tickets.domain.service.ClienteService;
import com.cinema.tickets.domain.strategy.ClienteStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ClienteServiceImpl implements ClienteService{

    final Logger log = Logger.getLogger(ClienteServiceImpl.class.getName());

    private final ClienteRepository clienteRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    ClienteStrategy clienteValidationStrategy;

    public ClienteServiceImpl(ClienteRepository clienteRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Cliente save(Cliente cliente) {
        log.info("Salvando novo cliente " + cliente.getNome());

        if (clienteValidationStrategy != null) {
            this.clienteValidationStrategy.validate(cliente);
        }

        cliente.setSenha(passwordEncoder.encode(cliente.getSenha()));

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

    @Override
    public Optional<Cliente> findClienteByEmail(String email) {
        log.info("Buscando cliente pelo email " + email);
        return clienteRepository.findByEmail(email);
    }

    @Override
    public Role saveRole(Role role) {

        log.info("Salvando nova role " + role.getNome());

        Role roleExistente = this.roleRepository.findByNome(role.getNome());

        if (roleExistente != null) {
            throw new BusinessException("Role já existente");
        }

        return roleRepository.save(role);
    }

    @Override
    public void addRole(String email, String nomeRole) {
        Optional<Cliente> cliente = this.clienteRepository.findByEmail(email);

        Role role = this.roleRepository.findByNome(nomeRole);

        log.info("Adicionando " + role.getNome() + " ao cliente " + (cliente.orElse(null) != null ? cliente.orElse(null).getNome() : null));

        if (cliente.isPresent()) {
            cliente.orElse(null).getRoles().add(role);
            this.clienteRepository.save(cliente.orElse(null));
            log.info("Role adicionado com sucesso");
        }
    }

    @Override
    public Role findRoleByNome(String nomeRole) {
        log.info("Buscando role pelo nome " + nomeRole);
        return this.roleRepository.findByNome(nomeRole);
    }

    @Override
    public Cliente login(String email, String senha) {
        log.info("Buscando cliente pelo email " + email);
        Optional<Cliente> cliente = this.clienteRepository.findByEmail(email);

        if (cliente.isPresent()) {
            if (passwordEncoder.matches(senha, cliente.get().getSenha())) {
                return cliente.get();
            }
        } else {
            throw new BusinessException("Cliente não encontrado");
        }

        return null;
    }
}
