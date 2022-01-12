package com.cinema.tickets.domain.service;

import com.cinema.tickets.domain.collection.Cliente;
import com.cinema.tickets.domain.repository.ClienteRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ClienteRepository clienteRepository;

    final Logger log = Logger.getLogger(CustomUserDetailsService.class.getName());

    public CustomUserDetailsService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Cliente> cliente = this.clienteRepository.findByEmail(email);

        if(cliente.isEmpty()) {
            log.info("Cliente não encontrado");
            throw new UsernameNotFoundException("Usuário não encontrado");
        } else {
            log.info("Cliente " + cliente.get().getEmail() + " encontrado");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        cliente.get().getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority(role.getNome())));

        return new org.springframework.security.core.userdetails.User(cliente.get().getEmail(), cliente.get().getSenha(), authorities);
    }
}
