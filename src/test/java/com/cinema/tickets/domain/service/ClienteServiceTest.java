package com.cinema.tickets.domain.service;

import com.cinema.tickets.domain.collection.Cliente;
import com.cinema.tickets.domain.collection.Endereco;
import com.cinema.tickets.domain.collection.Role;
import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.exception.NotFoundException;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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

    @Test
    @DisplayName("Deve procurar um cliente pelo id")
    public void findClienteByIdTest() {
        Cliente cliente = createCliente();

        when(clienteRepository.findById(Mockito.anyString())).thenReturn(java.util.Optional.of(cliente));

        Optional<Cliente> clienteEncontrado = clienteService.findById(cliente.getId());

        assertThat(clienteEncontrado.get().getId()).isNotNull();
        assertThat(clienteEncontrado.get().getNome()).isEqualTo(clienteEncontrado.get().getNome());
        assertThat(clienteEncontrado.get().getCpf()).isEqualTo(clienteEncontrado.get().getCpf());
        assertThat(clienteEncontrado.get().getDataNascimento()).isEqualTo(clienteEncontrado.get().getDataNascimento());
        assertThat(clienteEncontrado.get().getCelular()).isEqualTo(clienteEncontrado.get().getCelular());
        assertThat(clienteEncontrado.get().getEmail()).isEqualTo(clienteEncontrado.get().getEmail());
        assertThat(clienteEncontrado.get().getSenha()).isEqualTo(clienteEncontrado.get().getSenha());
        assertThat(clienteEncontrado.get().getEndereco().getRua()).isEqualTo(clienteEncontrado.get().getEndereco().getRua());
        assertThat(clienteEncontrado.get().getEndereco().getCep()).isEqualTo(clienteEncontrado.get().getEndereco().getCep());
        assertThat(clienteEncontrado.get().getEndereco().getNumero()).isEqualTo(clienteEncontrado.get().getEndereco().getNumero());
        assertThat(clienteEncontrado.get().getEndereco().getComplemento()).isEqualTo(clienteEncontrado.get().getEndereco().getComplemento());
        assertThat(clienteEncontrado.get().getEndereco().getBairro()).isEqualTo(clienteEncontrado.get().getEndereco().getBairro());
        assertThat(clienteEncontrado.get().getEndereco().getCidade()).isEqualTo(clienteEncontrado.get().getEndereco().getCidade());
        assertThat(clienteEncontrado.get().getEndereco().getEstado()).isEqualTo(clienteEncontrado.get().getEndereco().getEstado());
    }

    @Test
    @DisplayName("Deve deletar um cliente")
    public void deleteClienteTest() {
        Cliente cliente = createCliente();

        when(clienteRepository.findById(Mockito.anyString())).thenReturn(java.util.Optional.of(cliente));

        clienteService.delete(cliente.getId());

        verify(clienteRepository, times(1)).deleteById(cliente.getId());
    }

    @Test
    @DisplayName("Deve lançar um erro ao tentar deletar um cliente inexistente")
    public void deleteClienteInexistenteTest() {

        when(clienteRepository.findById(Mockito.anyString())).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> clienteService.delete("1"));
    }

    @Test
    @DisplayName("Deve atualizar um cliente")
    public void shouldUpdateCliente() {
        Cliente cliente = createCliente();

        when(clienteRepository.findById(Mockito.anyString())).thenReturn(java.util.Optional.of(cliente));

        when(clienteRepository.save(Mockito.any(Cliente.class))).thenReturn(cliente);

        Cliente clienteAtualizado = clienteService.update(cliente);

        assertThat(clienteAtualizado.getId()).isNotNull();
        assertThat(clienteAtualizado.getNome()).isEqualTo(clienteAtualizado.getNome());
        assertThat(clienteAtualizado.getCpf()).isEqualTo(clienteAtualizado.getCpf());
        assertThat(clienteAtualizado.getDataNascimento()).isEqualTo(clienteAtualizado.getDataNascimento());
        assertThat(clienteAtualizado.getCelular()).isEqualTo(clienteAtualizado.getCelular());
        assertThat(clienteAtualizado.getEmail()).isEqualTo(clienteAtualizado.getEmail());
        assertThat(clienteAtualizado.getSenha()).isEqualTo(clienteAtualizado.getSenha());
        assertThat(clienteAtualizado.getEndereco().getRua()).isEqualTo(clienteAtualizado.getEndereco().getRua());
        assertThat(clienteAtualizado.getEndereco().getCep()).isEqualTo(clienteAtualizado.getEndereco().getCep());
        assertThat(clienteAtualizado.getEndereco().getNumero()).isEqualTo(clienteAtualizado.getEndereco().getNumero());
        assertThat(clienteAtualizado.getEndereco().getComplemento()).isEqualTo(clienteAtualizado.getEndereco().getComplemento());
        assertThat(clienteAtualizado.getEndereco().getBairro()).isEqualTo(clienteAtualizado.getEndereco().getBairro());
        assertThat(clienteAtualizado.getEndereco().getCidade()).isEqualTo(clienteAtualizado.getEndereco().getCidade());
        assertThat(clienteAtualizado.getEndereco().getEstado()).isEqualTo(clienteAtualizado.getEndereco().getEstado());
    }

    @Test
    @DisplayName("Deve lançar um erro ao tentar atualizar um cliente inexistente")
    public void shouldThrowNotFoundExceptionWhenUpdateClienteInexistente() {

        when(clienteRepository.findById(Mockito.anyString())).thenReturn(java.util.Optional.empty());

        assertThrows(BusinessException.class, () -> clienteService.update(new Cliente()));
    }

    @Test
    @DisplayName("Deve listar todos os clientes")
    public void shouldListAllClientes() {
       Cliente cliente = createCliente();

        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        List<Cliente> clientesEncontrados = clienteService.findAll();

        assertThat(clientesEncontrados).isNotNull();
        assertThat(clientesEncontrados.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Deve encontrar um cliente pelo email")
    public void shouldFindClienteByEmail() {
        Cliente cliente = createCliente();

        when(clienteRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(cliente));

        Optional<Cliente> clienteEncontrado = clienteService.findClienteByEmail(cliente.getEmail());

        assertThat(clienteEncontrado).isNotNull();
        assertThat(clienteEncontrado.get().getEmail()).isEqualTo(cliente.getEmail());
    }

    @Test
    @DisplayName("Deve salvar role")
    public void shouldSaveRole() {

        Role role = new Role();
        role.setId("1");
        role.setNome("ROLE_ADMIN");

        when(roleRepository.save(Mockito.any(Role.class))).thenReturn(role);

        Role roleSalva = roleRepository.save(role);

        assertThat(roleSalva.getId()).isNotNull();
        assertThat(roleSalva.getNome()).isEqualTo(role.getNome());
    }


    @Test
    @DisplayName("Deve lançar um erro ao tentar salvar role existente")
    public void shouldThrowBusinessExceptionWhenSaveRoleExistente() {
        Role role = new Role();
        role.setId("1");
        role.setNome("ROLE_ADMIN");

        when(roleRepository.existsByNome(Mockito.anyString())).thenReturn(true);

        assertThrows(BusinessException.class, () -> clienteService.saveRole(role));

    }


    @Test
    @DisplayName("Deve achar role pelo nome")
    public void shouldFindRoleByName() {
        Role role = new Role();
        role.setId("1");
        role.setNome("ROLE_ADMIN");

        when(roleRepository.findByNome(Mockito.anyString())).thenReturn(role);

        Role roleEncontrada = clienteService.findRoleByNome(role.getNome());


        assertThat(roleEncontrada).isNotNull();
        assertThat(roleEncontrada.getNome()).isEqualTo(role.getNome());

    }





}

