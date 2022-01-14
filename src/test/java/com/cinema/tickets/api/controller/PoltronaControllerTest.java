package com.cinema.tickets.api.controller;

import com.cinema.tickets.annotations.WithMockAdmin;

import com.cinema.tickets.api.dto.PoltronaDTO;
import com.cinema.tickets.domain.collection.Poltrona;
import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.service.PoltronaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
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

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(PoltronaController.class)
@AutoConfigureMockMvc
public class PoltronaControllerTest {

    static final String POLTRONA_URL = "/api/v1/poltronas";

    @Autowired
    MockMvc mvc;

    @MockBean
    PoltronaService service;

    private PoltronaDTO createPoltronaDTO() {
        PoltronaDTO poltronaDTO = new PoltronaDTO();
        poltronaDTO.setCadeira("A1");
        poltronaDTO.setFileira("A");
        poltronaDTO.setStatus("DISPONIVEL");
        return poltronaDTO;
    }

    private Poltrona createPoltrona(){
        Poltrona poltrona = new Poltrona();
        poltrona.setId("1");
        poltrona.setCadeira("A1");
        poltrona.setFileira("A");
        poltrona.setStatus("DISPONIVEL");


        return poltrona;
    }

    @Test
    @WithMockAdmin
    @DisplayName("Deve cadastrar uma poltrona")
    public void shoudCreatePoltrona() throws Exception{

        PoltronaDTO poltronaDTO = createPoltronaDTO();

        Poltrona poltrona = createPoltrona();

        BDDMockito.given(service.save(Mockito.any(Poltrona.class))).willReturn(poltrona);

       String json =  new ObjectMapper().writeValueAsString(poltronaDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(POLTRONA_URL.concat("/cadastrar"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(String.valueOf(json));

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("cadeira").value(poltrona.getCadeira()))
                .andExpect(jsonPath("fileira").value(poltronaDTO.getFileira()))
                .andExpect(jsonPath("status").value(poltronaDTO.getStatus()));
    }

    @Test
    @DisplayName("Deve retornar bad request ao tentar cadastrar uma poltrona com dados inválidos")
    @WithMockAdmin
    public void shouldReturnBadRequestWhenCreatePoltronaWithInvalidData() throws Exception{
        String json = new ObjectMapper().writeValueAsString(new PoltronaDTO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(POLTRONA_URL.concat("/cadastrar"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("campos", hasSize(3)));
    }

    @Test
    @WithMockAdmin
    @DisplayName("Deve retornar bad request ao tentar cadastrar uma poltrona com dados inválidos")
    public void shouldReturnBadRequestWhenCreatePoltronaWithInvalidData2() throws Exception{
        String json = new ObjectMapper().writeValueAsString(new PoltronaDTO());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(POLTRONA_URL.concat("/cadastrar"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("campos", hasSize(3)));
    }

    @Test
    @DisplayName("Deve buscar uma poltrona por id")
    public void shouldGetPoltronaById() throws Exception{

        Poltrona poltrona = createPoltrona();

        BDDMockito.given(service.findById(Mockito.anyString())).willReturn(Optional.of(poltrona));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(POLTRONA_URL.concat("/1"));

        mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar not found ao tentar buscar uma poltrona por id")
    public void shouldReturnNotFoundWhenGetPoltronaById() throws Exception{

        BDDMockito.given(service.findById(Mockito.anyString())).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(POLTRONA_URL.concat("/1"));

        mvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve buscar todas as poltronas")
    public void shouldGetAllPoltronas() throws Exception{

        Poltrona poltrona = createPoltrona();
        List<Poltrona> poltronas = List.of(poltrona);

        BDDMockito.given(service.findAll()).willReturn(poltronas);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(POLTRONA_URL);

        mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("deve atualizar uma poltrona")
    @WithMockAdmin
    public void shouldUpdatePoltrona() throws Exception{

        PoltronaDTO poltronaDTO = createPoltronaDTO();

        Poltrona poltrona = createPoltrona();

        BDDMockito.given(service.findById(Mockito.anyString())).willReturn(Optional.of(poltrona));

        BDDMockito.given(service.update(Mockito.any(Poltrona.class))).willReturn(poltrona);

        String json = new ObjectMapper().writeValueAsString(poltronaDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(POLTRONA_URL.concat("/atualizar/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("deve retornar bad request ao tentar atualizar uma poltrona que não existe")
    @WithMockAdmin
    public void shouldReturnBadRequestWhenUpdatePoltrona() throws Exception{

        String json = new ObjectMapper().writeValueAsString(createPoltronaDTO());

        BDDMockito.given(service.findById(Mockito.any(String.class))).willReturn(Optional.empty());

        BDDMockito.given(service.update(Mockito.any(Poltrona.class))).willThrow(new BusinessException("Poltrona não encontrada, ou dados inválidos"));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(POLTRONA_URL.concat("/atualizar/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }
}
