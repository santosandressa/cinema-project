package com.cinema.tickets.api.controller;

import com.cinema.tickets.api.dto.ExibicaoDTO;
import com.cinema.tickets.api.dto.FilmeDTO;
import com.cinema.tickets.api.dto.IngressoDTO;
import com.cinema.tickets.api.dto.SalaDTO;
import com.cinema.tickets.domain.collection.*;
import com.cinema.tickets.domain.service.IngressoService;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(IngressoController.class)
@ImportAutoConfiguration( FeignAutoConfiguration.class)
public class IngressoControlletTest {

    static final String BASE_URL = "/api/v1/ingresso";


    @Autowired
    MockMvc mockMvc;

    @MockBean
    IngressoService ingressoService;

    private Ingresso createIngresso(){
        Ingresso ingresso = new Ingresso();
        ingresso.setId("1");
        ingresso.setValor(ingresso.getValor());
        ingresso.setMeiaEntrada(false);
        ingresso.setQuantidade(1);


        Exibicao exibicao = new Exibicao();
        exibicao.setId("1");
        exibicao.setDataExibicao(LocalDate.of(2022, 1, 22));


        Filme filme = new Filme();
        filme.setId("1");
        filme.setTitulo("Titulo");
        filme.setTituloOriginal("Titulo Original");
        filme.setDiretor("Diretor");
        filme.setDuracao("120");
        filme.setGenero("Ação");
        filme.setSinopse("Sinopse do filme 1");
        exibicao.setFilme(filme);

        Sala sala = new Sala();
        sala.setId("1");
        sala.setNumSala(1);
        sala.setCapacidade(216);
        sala.setSala3D(true);
        exibicao.setSala(sala);

        ingresso.setExibicao(exibicao);
        ingresso.setValorTotal(ingresso.getValorTotal());

        return ingresso;
    }

    private IngressoDTO createIngressoDTO(){
        IngressoDTO ingressoDTO = new IngressoDTO();
        ingressoDTO.setId("1");
        ingressoDTO.setValor(ingressoDTO.getValor());
        ingressoDTO.setMeiaEntrada(false);
        ingressoDTO.setQuantidade(1);


        ExibicaoDTO exibicaoDTO = new ExibicaoDTO();
        exibicaoDTO.setId("1");

        exibicaoDTO.setDataExibicao(LocalDate.of(2022, 1, 22));


        FilmeDTO filmeDTO = new FilmeDTO();
        filmeDTO.setId("1");
        filmeDTO.setTitulo("Titulo");
        filmeDTO.setTituloOriginal("Titulo Original");
        filmeDTO.setDiretor("Diretor");
        filmeDTO.setDuracao("120");
        filmeDTO.setGenero("Ação");
        filmeDTO.setSinopse("Sinopse do filme 1");
        exibicaoDTO.setFilme(filmeDTO);

        SalaDTO salaDTO = new SalaDTO();
        salaDTO.setId("1");
        salaDTO.setNumSala(1);
        salaDTO.setCapacidade(216);
        salaDTO.setSala3D(true);
        exibicaoDTO.setSala(salaDTO);

        ingressoDTO.setExibicao(exibicaoDTO);
        ingressoDTO.setValorTotal(ingressoDTO.getValorTotal());

        return ingressoDTO;
    }

    @Test
    @DisplayName("Deve retornar uma lista de ingressos")
    public void deveRetornarListaDeIngressos() throws Exception {
        List<Ingresso> ingressos = new ArrayList<>();

        BDDMockito.given(ingressoService.findAll()).willReturn(ingressos);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(BASE_URL)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Deve retornar um ingresso pelo id")
    public void deveRetornarUmIngresso() throws Exception {
        Ingresso ingresso = createIngresso();

        BDDMockito.given(ingressoService.findById(anyString())).willReturn(Optional.of(ingresso));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve deletar um ingresso pelo id")
    public void deveDeletarUmIngresso() throws Exception {
        Ingresso ingresso = createIngresso();

        BDDMockito.given(ingressoService.findById(anyString())).willReturn(Optional.of(ingresso));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(BASE_URL + "/1")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve criar um ingresso")
    public void deveCriarUmIngresso() throws Exception {
        IngressoDTO ingressoDTO = createIngressoDTO();

        Ingresso ingresso = createIngresso();

        BDDMockito.given(ingressoService.save(any(Ingresso.class))).willReturn(ingresso);

        String json =  new ObjectMapper().writeValueAsString(ingressoDTO);


        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isCreated());
    }
}

