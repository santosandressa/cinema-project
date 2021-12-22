package com.cinema.tickets.api.controller;


import com.cinema.tickets.domain.collection.Cliente;
import com.cinema.tickets.domain.collection.Endereco;
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

import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
@EnableWebMvc
public class ClienteControllerTest {

    static final String CLIENTE_API = "/api/v1/cliente";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ClienteService service;

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

    @Test
    @DisplayName("Deve cadastrar um cliente")
    public void shouldCreateCliente() throws Exception {

        Cliente cliente = createCliente();

        BDDMockito.given(service.save(any(Cliente.class))).willReturn(cliente);

        String json = new ObjectMapper().writeValueAsString(cliente);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(CLIENTE_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request).andExpect(status().isCreated())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("nome").value(cliente.getNome())).andExpect(jsonPath("cpf").value(cliente.getCpf())).andExpect(jsonPath("dataNascimento").value(cliente.getDataNascimento())).andExpect(jsonPath("celular").value(cliente.getCelular())).andExpect(jsonPath("email").value(cliente.getEmail())).andExpect(jsonPath("senha").value(cliente.getSenha())).andExpect(jsonPath("endereco.rua").value(cliente.getEndereco().getRua())).andExpect(jsonPath("endereco.cep").value(cliente.getEndereco().getCep())).andExpect(jsonPath("endereco.numero").value(cliente.getEndereco().getNumero())).andExpect(jsonPath("endereco.complemento").value(cliente.getEndereco().getComplemento())).andExpect(jsonPath("endereco.bairro").value(cliente.getEndereco().getBairro())).andExpect(jsonPath("endereco.cidade").value(cliente.getEndereco().getCidade())).andExpect(jsonPath("endereco.estado").value(cliente.getEndereco().getEstado()));
    }

    @Test
    @DisplayName("Deve lançar erro ao cadastrar um cliente com dados insuficientes")
    public void createInvalidCliente() throws Exception {
        String json = new ObjectMapper().writeValueAsString(new Cliente());


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(CLIENTE_API).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);


        mockMvc.perform(request).andExpect(status().isBadRequest()).andExpect(jsonPath("campos", hasSize(6)));
    }

    @Test
    @DisplayName("Deve lançar bad request ao cadastrar um cliente com dados já existentes")
    public void createClienteWithExistingEmail() throws Exception {
        Cliente cliente = createCliente();

        String json = new ObjectMapper().writeValueAsString(cliente);

        String message = "Email já cadastrado";

        BDDMockito.given(service.save(any(Cliente.class))).willThrow(new BusinessException(message));


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(CLIENTE_API).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

        mockMvc.perform(request).andExpect(status().isBadRequest()).andExpect(jsonPath("titulo").value(message));
    }

    @Test
    @DisplayName("Deve lançar bad request ao cadastrar um cliente com dados já existentes")
    public void createClienteWithExistingCpf() throws Exception {
        Cliente cliente = createCliente();

        String json = new ObjectMapper().writeValueAsString(cliente);

        String message = "CPF já cadastrado";

        BDDMockito.given(service.save(any(Cliente.class))).willThrow(new BusinessException(message));


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(CLIENTE_API).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

        mockMvc.perform(request).andExpect(status().isBadRequest()).andExpect(jsonPath("titulo").value(message));
    }

    @Test
    @DisplayName("Deve lançar not found ao cadastrar um cliente com dados já existentes")
    public void clienteNotFound() throws Exception {
        BDDMockito.given(service.findById(anyString())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(CLIENTE_API + "/{id}", "1").accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve deletar um cliente")
    public void deleteCliente() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setId("1");

        BDDMockito.given(service.findById(anyString())).willReturn(Optional.of(cliente));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(CLIENTE_API.concat("/" + "1")).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("Deve lançar not found ao deletar um cliente inexistente")
    public void deleteClienteNotFound() throws Exception {

        BDDMockito.given(service.findById(anyString())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(CLIENTE_API.concat("/" + "1")).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve atualizar um cliente")
    public void updateCliente() throws Exception {

        Cliente cliente = createCliente();
        cliente.setId("1");

        BDDMockito.given(service.findById(anyString())).willReturn(Optional.of(cliente));
        BDDMockito.given(service.save(any(Cliente.class))).willReturn(cliente);


        String json = new ObjectMapper().writeValueAsString(cliente);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(CLIENTE_API.concat("/" + "1")).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(json);

        mockMvc.perform(request).andExpect(status().isOk()).andExpect(jsonPath("id").value("1")).andExpect(jsonPath("nome").value(cliente.getNome())).andExpect(jsonPath("cpf").value(cliente.getCpf())).andExpect(jsonPath("dataNascimento").value(cliente.getDataNascimento())).andExpect(jsonPath("celular").value(cliente.getCelular())).andExpect(jsonPath("email").value(cliente.getEmail())).andExpect(jsonPath("senha").value(cliente.getSenha())).andExpect(jsonPath("endereco.rua").value(cliente.getEndereco().getRua())).andExpect(jsonPath("endereco.cep").value(cliente.getEndereco().getCep())).andExpect(jsonPath("endereco.numero").value(cliente.getEndereco().getNumero())).andExpect(jsonPath("endereco.complemento").value(cliente.getEndereco().getComplemento())).andExpect(jsonPath("endereco.bairro").value(cliente.getEndereco().getBairro())).andExpect(jsonPath("endereco.cidade").value(cliente.getEndereco().getCidade())).andExpect(jsonPath("endereco.estado").value(cliente.getEndereco().getEstado()));
    }


    @Test
    @DisplayName("Deve lançar not found ao atualizar um cliente inexistente")
    public void updateClienteNotFound() throws Exception {

        BDDMockito.given(service.findById(anyString())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(CLIENTE_API.concat("/" + "1")).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request).andExpect(status().isBadRequest());
    }
}
