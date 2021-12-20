package com.cinema.tickets.domain.service;

import com.cinema.tickets.domain.collection.Cliente;
import com.cinema.tickets.domain.collection.Endereco;
import com.cinema.tickets.domain.repository.ClienteRepository;
import com.cinema.tickets.domain.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ClienteServiceTest {

    ClienteService clienteService;

    @MockBean
    ClienteRepository clienteRepository;

    @BeforeEach
    public void setUp() {
        clienteService = new ClienteServiceImpl(clienteRepository);
    }

    private Cliente createCliente() {
        Cliente clienteSalvo = new Cliente();
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

        return clienteSalvo;
    }

    @Test
    @DisplayName("Deve salvar um cliente")
    public void saveClienteTest(){
        Cliente cliente = createCliente();
        cliente.setId("1");

        when(clienteRepository.existsByEmail(Mockito.anyString())).thenReturn(false);
        when(clienteRepository.existsByCpf(Mockito.anyString())).thenReturn(false);

        when(clienteRepository.save(cliente)).thenReturn(cliente);

        Cliente clienteSalvo = clienteService.save(cliente);

        assertThat(clienteSalvo.getId()).isNotNull();
        assertThat(clienteSalvo.getNome()).isEqualTo(cliente.getNome());
        assertThat(clienteSalvo.getCpf()).isEqualTo(cliente.getCpf());
        assertThat(clienteSalvo.getDataNascimento()).isEqualTo(cliente.getDataNascimento());
        assertThat(clienteSalvo.getCelular()).isEqualTo(cliente.getCelular());
        assertThat(clienteSalvo.getEmail()).isEqualTo(cliente.getEmail());
        assertThat(clienteSalvo.getSenha()).isEqualTo(cliente.getSenha());
        assertThat(clienteSalvo.getEndereco().getRua()).isEqualTo(cliente.getEndereco().getRua());
        assertThat(clienteSalvo.getEndereco().getCep()).isEqualTo(cliente.getEndereco().getCep());
        assertThat(clienteSalvo.getEndereco().getNumero()).isEqualTo(cliente.getEndereco().getNumero());
        assertThat(clienteSalvo.getEndereco().getComplemento()).isEqualTo(cliente.getEndereco().getComplemento());
        assertThat(clienteSalvo.getEndereco().getBairro()).isEqualTo(cliente.getEndereco().getBairro());
        assertThat(clienteSalvo.getEndereco().getCidade()).isEqualTo(cliente.getEndereco().getCidade());
        assertThat(clienteSalvo.getEndereco().getEstado()).isEqualTo(cliente.getEndereco().getEstado());
    }




}

