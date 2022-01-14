package com.cinema.tickets.domain.service;

import com.cinema.tickets.domain.collection.Cliente;
import com.cinema.tickets.domain.collection.Endereco;
import com.cinema.tickets.domain.repository.ClienteRepository;
import com.cinema.tickets.domain.repository.RoleRepository;
import com.cinema.tickets.domain.service.impl.ClienteServiceImpl;
import com.cinema.tickets.domain.strategy.ClienteStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ClienteServiceTest {

    ClienteService clienteService;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    ClienteRepository clienteRepository;

    @MockBean
    ClienteStrategy clienteValidationStrategy;

    @BeforeEach
    public void setUp() {

        clienteService = new ClienteServiceImpl(clienteRepository, roleRepository, passwordEncoder, clienteValidationStrategy);
    }

    private Cliente createCliente() {
        Cliente clienteSalvo = new Cliente();
        clienteSalvo.setId("1");
        clienteSalvo.setNome("Luana Antonella Santos");
        clienteSalvo.setCpf("459.623.359-45");
        clienteSalvo.setDataNascimento("04/08/1963");
        clienteSalvo.setCelular("(48) 99853-5719");
        clienteSalvo.setEmail("luanaantonellasantos_@trbvm.com");
        clienteSalvo.setSenha("4hj1L0NkbJ");

        Endereco endereco = new Endereco();
        endereco.setRua("Rua dos Gaviões");
        endereco.setCep("85800-000");
        endereco.setNumero("585");
        endereco.setComplemento("Apto. 5");
        endereco.setBairro("Cidade Universitária Pedra Branca");
        endereco.setCidade("Palhoça");
        endereco.setEstado("Santa Catarina");

        clienteSalvo.setEndereco(endereco);

        clienteSalvo.setSenha(passwordEncoder.encode(clienteSalvo.getSenha()));

        return clienteSalvo;
    }

    @Test
    @DisplayName("Deve salvar um cliente")
    public void saveClienteTest() {
        Cliente cliente = createCliente();

        when(clienteRepository.existsByEmail(Mockito.anyString())).thenReturn(false);

        when(clienteRepository.existsByCpf(Mockito.anyString())).thenReturn(false);

        clienteService.save(cliente);

        assertThat(cliente.getId()).isNotNull();
        assertThat(cliente.getNome()).isEqualTo(cliente.getNome());
        assertThat(cliente.getCpf()).isEqualTo(cliente.getCpf());
        assertThat(cliente.getDataNascimento()).isEqualTo(cliente.getDataNascimento());
        assertThat(cliente.getCelular()).isEqualTo(cliente.getCelular());
        assertThat(cliente.getEmail()).isEqualTo(cliente.getEmail());
        assertThat(cliente.getSenha()).isEqualTo(cliente.getSenha());
        assertThat(cliente.getEndereco().getRua()).isEqualTo(cliente.getEndereco().getRua());
        assertThat(cliente.getEndereco().getCep()).isEqualTo(cliente.getEndereco().getCep());
        assertThat(cliente.getEndereco().getNumero()).isEqualTo(cliente.getEndereco().getNumero());
        assertThat(cliente.getEndereco().getComplemento()).isEqualTo(cliente.getEndereco().getComplemento());
        assertThat(cliente.getEndereco().getBairro()).isEqualTo(cliente.getEndereco().getBairro());
        assertThat(cliente.getEndereco().getCidade()).isEqualTo(cliente.getEndereco().getCidade());
        assertThat(cliente.getEndereco().getEstado()).isEqualTo(cliente.getEndereco().getEstado());
    }


}

