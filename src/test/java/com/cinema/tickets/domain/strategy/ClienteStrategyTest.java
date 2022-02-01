package com.cinema.tickets.domain.strategy;

import com.cinema.tickets.domain.collection.Cliente;
import com.cinema.tickets.domain.collection.Endereco;
import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.exception.NotFoundException;
import com.cinema.tickets.domain.repository.ClienteRepository;

import com.cinema.tickets.domain.strategy.impl.ClienteStrategyImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class ClienteStrategyTest {

    ClienteStrategy clienteStrategy;

    @MockBean
    ClienteRepository clienteRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp(){
        clienteStrategy = new ClienteStrategyImpl(clienteRepository);
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
        endereco.setLogradouro("Rua dos Gaviões");
        endereco.setCep("85800-000");
        endereco.setNumero("585");
        endereco.setComplemento("Apto. 5");
        endereco.setBairro("Cidade Universitária Pedra Branca");
        endereco.setLocalidade("Palhoça");
        endereco.setUf("Santa Catarina");

        clienteSalvo.setEndereco(endereco);

        clienteSalvo.setSenha(passwordEncoder.encode(clienteSalvo.getSenha()));

        return clienteSalvo;
    }

    @Test
    @DisplayName("Deve achar um cliente pelo id")
    public void shouldFindById(){
        Cliente cliente = createCliente();

        when(clienteRepository.findById(anyString())).thenReturn(Optional.of(cliente));

        clienteStrategy.findById(cliente.getId());

        assertThat(cliente.getId()).isEqualTo(cliente.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar achar um cliente pelo id inexistente")
    public void shouldThrowException(){
        String id = "1";

        when(clienteRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> clienteStrategy.findById(id));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar achar um cliente com email já cadastrado")
    public void shouldThrowExceptionEmailExists(){
        Cliente cliente = createCliente();

        when(clienteRepository.existsByEmail(anyString())).thenReturn(true);

        assertThrows(BusinessException.class, () -> clienteStrategy.validate(cliente));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar achar um cliente com cpf já cadastrado")
    public void shouldThrowExceptionCpfExists(){
        Cliente cliente = createCliente();

        when(clienteRepository.existsByCpf(anyString())).thenReturn(true);

        assertThrows(BusinessException.class, () -> clienteStrategy.validate(cliente));
    }

    @Test
    @DisplayName("Deve lançar exceção quando endereço não for preenchido")
    public void shouldThrowExceptionEnderecoNull(){
        Cliente cliente = createCliente();
        cliente.setEndereco(null);

        assertThrows(BusinessException.class, () -> clienteStrategy.validate(cliente));
    }

    @Test
    @DisplayName("Deve validar um cliente")
    public void shouldValidateCliente(){
        Cliente cliente = createCliente();

        when(clienteRepository.existsByEmail(anyString())).thenReturn(false);

        when(clienteRepository.existsByCpf(anyString())).thenReturn(false);

        clienteStrategy.validate(cliente);
    }

}
