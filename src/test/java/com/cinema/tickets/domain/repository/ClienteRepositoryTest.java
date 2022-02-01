package com.cinema.tickets.domain.repository;

import com.cinema.tickets.domain.collection.Cliente;
import com.cinema.tickets.domain.collection.Endereco;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class ClienteRepositoryTest {

    @Autowired
    ClienteRepository clienteRepository;

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

        return clienteSalvo;
    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando o cliente for encontrado")
    public void findById_shouldReturnTrue_whenClienteIsFound() {
        Cliente cliente = createCliente();
        clienteRepository.save(cliente);

        String id = "1";

        boolean exists = clienteRepository.existsById(id);

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar falso quando o cliente não for encontrado")
    public void findById_shouldReturnFalse_whenClienteIsNotFound() {
        String id = "8";

        boolean exists = clienteRepository.existsById(id);

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando o email for encontrado")
    public void findByEmail_shouldReturnTrue_whenEmailIsFound() {
        Cliente cliente = createCliente();
        clienteRepository.save(cliente);

        String email = "luanaantonellasantos_@trbvm.com";

        boolean exists = clienteRepository.existsByEmail(email);

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar falso quando o email não for encontrado")
    public void findByEmail_shouldReturnFalse_whenEmailIsNotFound() {
        String email = "a@a.com";

        boolean exists = clienteRepository.existsByEmail(email);

        assertThat(exists).isFalse();
    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando o cpf for encontrado")
    public void findByCpf_shouldReturnTrue_whenCpfIsFound() {
        Cliente cliente = createCliente();
        clienteRepository.save(cliente);

        String cpf = "459.623.359-45";

        boolean exists = clienteRepository.existsByCpf(cpf);

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Deve retornar falso quando o cpf não for encontrado")
    public void findByCpf_shouldReturnFalse_whenCpfIsNotFound() {
        String cpf = "123.456.789-10";

        boolean exists = clienteRepository.existsByCpf(cpf);

        assertThat(exists).isFalse();
    }

}
