package com.cinema.tickets.api.controller;

import com.cinema.tickets.api.dto.ExibicaoDTO;
import com.cinema.tickets.api.dto.FilmeDTO;
import com.cinema.tickets.api.dto.SalaDTO;
import com.cinema.tickets.domain.collection.Exibicao;
import com.cinema.tickets.domain.collection.Filme;
import com.cinema.tickets.domain.collection.Horarios;
import com.cinema.tickets.domain.collection.Sala;
import com.cinema.tickets.domain.service.ExibicaoService;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(ExibicaoController.class)
@AutoConfigureMockMvc
public class ExibicaoControllerTest {

    static final String URL_BASE = "/api/v1/exibicao";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ExibicaoService exibicaoService;

    private ExibicaoDTO createExibicaoDto(){
        ExibicaoDTO exibicaoDTO = new ExibicaoDTO();
        exibicaoDTO.setDataExibicao(LocalDate.of(2022, 1, 22));


        FilmeDTO filmeDTO = new FilmeDTO();
        filmeDTO.setTitulo("Titulo");
        filmeDTO.setTituloOriginal("Titulo Original");
        filmeDTO.setDiretor("Diretor");
        filmeDTO.setDuracao("120");
        filmeDTO.setGenero("Ação");
        filmeDTO.setSinopse("Sinopse do filme 1");


        SalaDTO salaDTO = new SalaDTO();
        salaDTO.setNumSala(1);
        salaDTO.setCapacidade(216);
        salaDTO.setSala3D(true);

        exibicaoDTO.setFilme(exibicaoDTO.getFilme());

        Horarios horarios = new Horarios();
        horarios.setHorario(LocalTime.of(14,30));
        exibicaoDTO.setHorarios(exibicaoDTO.getHorarios());
        return exibicaoDTO;
    }


    private Exibicao createExibicao(){
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


        Sala sala = new Sala();
        sala.setId("1");
        sala.setNumSala(1);
        sala.setCapacidade(216);
        sala.setSala3D(true);


        exibicao.setFilme(exibicao.getFilme());

        Horarios horarios = new Horarios();
        horarios.setHorario(LocalTime.of(14,30));

        exibicao.setHorarios(exibicao.getHorarios());

        return exibicao;
    }


    @Test
    @DisplayName("deve cadastrar uma nova exibição")
    public void shouldCreateExibicao() throws Exception{
        ExibicaoDTO exibicaoDTO = createExibicaoDto();

        Exibicao exibicao = createExibicao();

        BDDMockito.given(exibicaoService.save(any(Exibicao.class))).willReturn(exibicao);

        String json = new ObjectMapper().writeValueAsString(exibicaoDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(URL_BASE)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("dataExibicao").value(exibicao.getDataExibicao().toString()))
                .andExpect(jsonPath("horarios").value(exibicao.getHorarios()));
    }

    @Test
    @DisplayName("deve retornar uma lista de exibições")
    public void shouldListAll() throws Exception {
        List<Exibicao> exibicoes = List.of(createExibicao());

        BDDMockito.given(exibicaoService.getAll()).willReturn(exibicoes);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URL_BASE.concat("/listar"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }


    @Test
    @DisplayName("Deve buscar uma exibição por id")
    public void shouldListById() throws Exception{
        Exibicao exibicao = createExibicao();

        BDDMockito.given(exibicaoService.getById(any(String.class))).willReturn(Optional.of(exibicao));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URL_BASE.concat("/listar/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao buscar uma exibição por id inexistente")
    public void shouldThrowExceptionWhenListById() throws Exception{
        BDDMockito.given(exibicaoService.getById(any(String.class))).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(URL_BASE.concat("/listar/2"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve deletar uma exibição por id")
    public void shouldDeleteById() throws Exception{
        Exibicao exibicao = createExibicao();

        BDDMockito.given(exibicaoService.getById(any(String.class))).willReturn(Optional.of(exibicao));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(URL_BASE.concat("/deletar/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao deletar uma exibição por id inexistente")
    public void shouldThrowExceptionWhenDeleteById() throws Exception{
        BDDMockito.given(exibicaoService.getById(any(String.class))).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(URL_BASE.concat("/deletar/2"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve atualizar uma exibição por id")
    public void shouldUpdateById() throws Exception{
        ExibicaoDTO exibicaoDTO = createExibicaoDto();

        Exibicao exibicao = createExibicao();

        BDDMockito.given(exibicaoService.getById(any(String.class))).willReturn(Optional.of(exibicao));

        BDDMockito.given(exibicaoService.update(any(Exibicao.class))).willReturn(exibicao);

        String json = new ObjectMapper().writeValueAsString(exibicaoDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(URL_BASE.concat("/atualizar" + "/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao atualizar uma exibição por id inexistente")
    public void shouldThrowExceptionWhenUpdateById() throws Exception{
        ExibicaoDTO exibicaoDTO = createExibicaoDto();

        BDDMockito.given(exibicaoService.getById(any(String.class))).willReturn(Optional.empty());

        BDDMockito.given(exibicaoService.update(any(Exibicao.class))).willReturn(null);

        String json = new ObjectMapper().writeValueAsString(exibicaoDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(URL_BASE.concat("/atualizar" + "/2"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }


}
