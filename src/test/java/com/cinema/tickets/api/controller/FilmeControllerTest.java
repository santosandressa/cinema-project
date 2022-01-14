package com.cinema.tickets.api.controller;

import com.cinema.tickets.annotations.WithMockAdmin;
import com.cinema.tickets.api.dto.FilmeDTO;
import com.cinema.tickets.domain.collection.Filme;
import com.cinema.tickets.domain.service.FilmeService;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(FilmeController.class)
@AutoConfigureMockMvc
@EnableWebMvc
public class FilmeControllerTest {

    static final String FILME_API = "/api/v1/filmes";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    FilmeService filmeService;

    private FilmeDTO createFilmeDTO() {
        FilmeDTO filmeDTO = new FilmeDTO();
        filmeDTO.setTitulo("Percy Jackson e o mar de monstros");
        filmeDTO.setTituloOriginal("Percy Jackson and the Sea of Monsters");
        filmeDTO.setDiretor("Thor Freudenthal");
        filmeDTO.setGenero("Ficção");
        filmeDTO.setSinopse("Para salvar o mundo, Percy e seus amigos precisam encontrar o poderoso e mágico Velocino de Ouro. Para isso, eles embarcam em uma perigosa odisseia nas águas nunca navegadas do Mar dos Monstros - conhecido pelos humanos como Triângulo das Bermudas.");
        filmeDTO.setDuracao("107 min");

        return filmeDTO;
    }

    private Filme createFilme() {
        Filme filme = new Filme();
        filme.setId("1");
        filme.setTitulo("Percy Jackson e o mar de monstros");
        filme.setTituloOriginal("Percy Jackson and the Sea of Monsters");
        filme.setDiretor("Thor Freudenthal");
        filme.setGenero("Ficção");
        filme.setSinopse("Para salvar o mundo, Percy e seus amigos precisam encontrar o poderoso e mágico Velocino de Ouro. Para isso, eles embarcam em uma perigosa odisseia nas águas nunca navegadas do Mar dos Monstros - conhecido pelos humanos como Triângulo das Bermudas.");
        filme.setDuracao("107 min");

        return filme;
    }

    @Test
    @WithMockAdmin
    @DisplayName("Deve cadastrar um filme")
    public void shouldCreateFilme() throws Exception {

        FilmeDTO filmeDTO = createFilmeDTO();

        Filme filme = createFilme();

        BDDMockito.given(filmeService.save(any(Filme.class))).willReturn(filme);

        String json = new ObjectMapper().writeValueAsString(filmeDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(FILME_API.concat("/cadastrar"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(filme.getId()))
                .andExpect(jsonPath("$.titulo").value(filmeDTO.getTitulo()))
                .andExpect(jsonPath("$.tituloOriginal").value(filmeDTO.getTituloOriginal()))
                .andExpect(jsonPath("$.diretor").value(filmeDTO.getDiretor()))
                .andExpect(jsonPath("$.genero").value(filmeDTO.getGenero()))
                .andExpect(jsonPath("$.sinopse").value(filmeDTO.getSinopse()))
                .andExpect(jsonPath("$.duracao").value(filmeDTO.getDuracao()));
    }

    @Test
    @DisplayName("Deve deletar um filme")
    @WithMockAdmin
    public void shouldDeleteFilme() throws Exception {

        Filme filme = createFilme();

        BDDMockito.given(filmeService.findById(any(String.class))).willReturn(Optional.of(filme));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(FILME_API.concat("/deletar/" + "1"));

        mockMvc.perform(request).andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("deve buscar um filme por id")
    public void shouldGetFilmeById() throws Exception{

        Filme filme = createFilme();

        BDDMockito.given(filmeService.findById(any(String.class))).willReturn(Optional.of(filme));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(FILME_API.concat( "/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar not found ao buscar um filme por id que não existe")
    public void shouldNotFoundFilmeById() throws Exception{

        BDDMockito.given(filmeService.findById(any(String.class))).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(FILME_API.concat("/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("Deve buscar todos os filmes")
    public void shouldGetAllFilmes() throws Exception{

        Filme filme = createFilme();

        BDDMockito.given(filmeService.findAll()).willReturn(List.of(filme));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(FILME_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar not found ao deletar um filme que não existe")
    @WithMockAdmin
    public void shouldReturnNotFoundDeleteFilme() throws Exception{

        BDDMockito.given(filmeService.findById(any(String.class))).willReturn(Optional.empty());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(FILME_API.concat("/deletar/" + "1"));

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Deve  atualizar um filme")
    @WithMockAdmin
    public void shouldUpdateFilme() throws Exception{
        FilmeDTO filmeDTO = createFilmeDTO();

        Filme filme = createFilme();

        BDDMockito.given(filmeService.findById(any(String.class))).willReturn(Optional.of(filme));

        BDDMockito.given(filmeService.update(any(Filme.class))).willReturn(filme);

        String json = new ObjectMapper().writeValueAsString(filmeDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(FILME_API.concat("/atualizar/1"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isOk());
    }


}
