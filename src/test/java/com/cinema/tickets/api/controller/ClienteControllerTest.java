package com.cinema.tickets.api.controller;


import com.cinema.tickets.annotations.WithMockAdmin;
import com.cinema.tickets.api.dto.ClienteDTO;
import com.cinema.tickets.domain.collection.Cliente;
import com.cinema.tickets.domain.collection.Endereco;
import com.cinema.tickets.domain.collection.Role;
import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(ClienteController.class)
@AutoConfigureMockMvc
@EnableWebMvc
public class ClienteControllerTest {

    static final String CLIENTE_API = "/api/v1/cliente";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ClienteService service;

    private ClienteDTO createClienteDTO() {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNome("Luana Antonella Santos");
        clienteDTO.setCpf("459.623.359-45");
        clienteDTO.setDataNascimento("04/08/1963");
        clienteDTO.setCelular("(48) 99853-5719");
        clienteDTO.setEmail("luanaantonellasantos_@trbvm.com");
        clienteDTO.setSenha("4hj1L0NkbJ");

        Endereco endereco = new Endereco();
        endereco.setRua("Rua dos Gaviões");
        endereco.setCep("85800-000");
        endereco.setNumero("585");
        endereco.setComplemento("Apto. 5");
        endereco.setBairro("Cidade Universitária Pedra Branca");
        endereco.setCidade("Palhoça");
        endereco.setEstado("Santa Catarina");

        clienteDTO.setEndereco(endereco);

        return clienteDTO;
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

        return clienteSalvo;
    }

    public Role createRole() {
        Role role = new Role();
        role.setId("1");
        role.setNome("ROLE_ADMIN");

        return role;
    }

    @Test
    @DisplayName("Deve cadastrar um cliente")
    public void shouldCreateCliente() throws Exception {

        ClienteDTO clienteDTO = createClienteDTO();

        Cliente cliente = createCliente();

        service.addRole("luanaantonellasantos_@trbvm.com", "ROLE_ADMIN");

        BDDMockito.given(service.save(any(Cliente.class))).willReturn(cliente);

        String json = new ObjectMapper().writeValueAsString(clienteDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(CLIENTE_API.concat("/cadastrar"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request).andExpect(status().isCreated())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("nome").value(clienteDTO.getNome()))
                .andExpect(jsonPath("cpf").value(clienteDTO.getCpf()))
                .andExpect(jsonPath("dataNascimento").value(clienteDTO.getDataNascimento()))
                .andExpect(jsonPath("celular").value(clienteDTO.getCelular()))
                .andExpect(jsonPath("email").value(clienteDTO.getEmail()))
                .andExpect(jsonPath("senha").value(clienteDTO.getSenha()))
                .andExpect(jsonPath("endereco.rua").value(clienteDTO.getEndereco().getRua()))
                .andExpect(jsonPath("endereco.cep").value(clienteDTO.getEndereco().getCep()))
                .andExpect(jsonPath("endereco.numero").value(clienteDTO.getEndereco().getNumero()))
                .andExpect(jsonPath("endereco.complemento").value(clienteDTO.getEndereco().getComplemento()))
                .andExpect(jsonPath("endereco.bairro").value(clienteDTO.getEndereco().getBairro()))
                .andExpect(jsonPath("endereco.cidade").value(clienteDTO.getEndereco().getCidade()))
                .andExpect(jsonPath("endereco.estado").value(clienteDTO.getEndereco().getEstado()));
    }

    @Test
    @DisplayName("Deve lançar erro ao cadastrar um cliente com dados insuficientes")
    public void createInvalidCliente() throws Exception {
        String json = new ObjectMapper().writeValueAsString(new Cliente());


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(CLIENTE_API.concat("/cadastrar")).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);


        mockMvc.perform(request).andExpect(status().isBadRequest()).andExpect(jsonPath("campos", hasSize(6)));
    }

    @Test
    @DisplayName("Deve lançar bad request ao cadastrar um cliente com dados já existentes")
    public void createClienteWithExistingEmail() throws Exception {

        ClienteDTO clienteDTO = createClienteDTO();


        String json = new ObjectMapper().writeValueAsString(clienteDTO);

        String message = "Email já cadastrado";

        BDDMockito.given(service.save(any(Cliente.class))).willThrow(new BusinessException(message));


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(CLIENTE_API.concat("/cadastrar")).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

        mockMvc.perform(request).andExpect(status().isBadRequest()).andExpect(jsonPath("titulo").value(message));
    }

    @Test
    @DisplayName("Deve lançar bad request ao cadastrar um cliente com dados já existentes")
    public void createClienteWithExistingCpf() throws Exception {
        ClienteDTO clienteDTO = createClienteDTO();

        String json = new ObjectMapper().writeValueAsString(clienteDTO);

        String message = "CPF já cadastrado";

        BDDMockito.given(service.save(any(Cliente.class))).willThrow(new BusinessException(message));


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(CLIENTE_API.concat("/cadastrar")).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

        mockMvc.perform(request).andExpect(status().isBadRequest()).andExpect(jsonPath("titulo").value(message));
    }

    @Test
    @DisplayName("Deve lançar bad request ao cadastrar um cliente com dados já existentes")
    public void clienteNotFound() throws Exception {
        BDDMockito.given(service.findById(anyString())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(CLIENTE_API.concat("/cadastrar")).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }

    @Test
    @WithMockAdmin
    @DisplayName("Deve deletar um cliente")
    public void deleteCliente() throws Exception {
        Cliente cliente = new Cliente();

        BDDMockito.given(service.findById(anyString())).willReturn(Optional.of(cliente));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(CLIENTE_API.concat("/deletar/" + "1")).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNoContent());
    }


    @Test
    @WithMockAdmin
    @DisplayName("Deve lançar not found ao deletar um cliente inexistente")
    public void deleteClienteNotFound() throws Exception {

        BDDMockito.given(service.findById(anyString())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(CLIENTE_API.concat("/deletar/" + "1")).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNotFound());
    }


    @Test
    @WithMockAdmin
    @DisplayName("Deve atualizar um cliente")
    public void updateCliente() throws Exception {
        ClienteDTO clienteDTO = createClienteDTO();

        Cliente cliente = new Cliente();

        BDDMockito.given(service.findById(anyString())).willReturn(Optional.of(cliente));

        BDDMockito.given(service.update(any(Cliente.class))).willReturn(cliente);

        String json = new ObjectMapper().writeValueAsString(clienteDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(CLIENTE_API.concat("/atualizar" + "/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request).andExpect(status().isOk());

    }

    @Test
    @WithMockAdmin
    @DisplayName("Deve lançar bad request ao atualizar um cliente inexistente")
    public void updateClienteBadRequest() throws Exception {

        String json = new ObjectMapper().writeValueAsString(createClienteDTO());

        BDDMockito.given(service.findById(anyString())).willReturn(Optional.empty());

        BDDMockito.given(service.update(any(Cliente.class))).willThrow(new BusinessException("Cliente não encontrado, ou dados inválidos"));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(CLIENTE_API.concat("/atualizar" + "/7"))
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }


    @Test
    @WithMockAdmin
    @DisplayName("Deve buscar um cliente pelo id")
    public void shouldGetClienteById() throws Exception {

        Cliente cliente = createCliente();

        BDDMockito.given(service.findById(anyString())).willReturn(Optional.of(cliente));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(CLIENTE_API.concat("/" + "1")).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk());
    }

    @Test
    @WithMockAdmin
    @DisplayName("Deve lançar not found ao buscar um cliente inexistente")
    public void shouldThrowNotFoundGetClienteById() throws Exception {

        BDDMockito.given(service.findById(anyString())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(CLIENTE_API.concat("/" + "1")).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    @WithMockAdmin
    @DisplayName("Deve buscar todos os clientes")
    public void shouldGetTodosClientes() throws Exception {

        List<Cliente> clientes = new ArrayList<>();

        BDDMockito.given(service.findAll()).willReturn(clientes);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(CLIENTE_API.concat("/listar")).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isOk()).andReturn();
    }

    @Test
    @WithMockAdmin
    @DisplayName("Deve salvar um role")
    public void shoulSaveARole() throws Exception {

        Role role = createRole();

        BDDMockito.given(service.saveRole(any(Role.class))).willReturn(role);

        String json = new ObjectMapper().writeValueAsString(role);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(CLIENTE_API.concat("/role/salvar"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(role.getId()))
                .andExpect(jsonPath("$.nome").value(role.getNome()));
    }
}
