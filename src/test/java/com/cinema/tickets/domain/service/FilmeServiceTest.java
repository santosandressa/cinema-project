package com.cinema.tickets.domain.service;

import com.cinema.tickets.domain.collection.Filme;
import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.exception.NotFoundException;
import com.cinema.tickets.domain.repository.FilmeRepository;
import com.cinema.tickets.domain.service.impl.FilmeServiceImpl;
import com.cinema.tickets.domain.strategy.FilmeStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class FilmeServiceTest {

   FilmeService filmeService;

    @MockBean
    FilmeRepository filmeRepository;

    @MockBean
    FilmeStrategy filmeStrategy;

    @BeforeEach
    public void setUp() {

        filmeService = new FilmeServiceImpl(filmeStrategy, filmeRepository);
    }

    private Filme createFilme(){
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
    @DisplayName("Deve salvar um filme")
    public void shouldSaveFilme() {
        Filme filme = createFilme();

        when(filmeRepository.existsByTitulo(Mockito.anyString())).thenReturn(false);

        when(filmeRepository.save(filme)).thenReturn(filme);

        Filme filmeSalvo = filmeService.save(filme);

        assertThat(filmeSalvo.getId()).isNotNull();
        assertThat(filmeSalvo.getTitulo()).isEqualTo(filme.getTitulo());
        assertThat(filmeSalvo.getTituloOriginal()).isEqualTo(filme.getTituloOriginal());
        assertThat(filmeSalvo.getDiretor()).isEqualTo(filme.getDiretor());
        assertThat(filmeSalvo.getGenero()).isEqualTo(filme.getGenero());
        assertThat(filmeSalvo.getSinopse()).isEqualTo(filme.getSinopse());
        assertThat(filmeSalvo.getDuracao()).isEqualTo(filme.getDuracao());
    }

    @Test
    @DisplayName("Deve procurar um filme pelo id")
    public void shouldFindFilmeById() {
        Filme filme = createFilme();

        when(filmeRepository.findById(Mockito.anyString())).thenReturn(java.util.Optional.of(filme));

        Optional<Filme> filmeEncontrado = filmeService.findById(filme.getId());

        assertThat(filmeEncontrado.orElse(null)).isNotNull();
        assertThat(filmeEncontrado.get().getTitulo()).isEqualTo(filme.getTitulo());
        assertThat(filmeEncontrado.get().getTituloOriginal()).isEqualTo(filme.getTituloOriginal());
        assertThat(filmeEncontrado.get().getDiretor()).isEqualTo(filme.getDiretor());
        assertThat(filmeEncontrado.get().getGenero()).isEqualTo(filme.getGenero());
        assertThat(filmeEncontrado.get().getSinopse()).isEqualTo(filme.getSinopse());
        assertThat(filmeEncontrado.get().getDuracao()).isEqualTo(filme.getDuracao());
    }

    @Test
    @DisplayName("Deve listar todos os filmes")
    public void shouldListAllFilmes(){
        Filme filme = createFilme();

        when(filmeRepository.findAll()).thenReturn(List.of(filme));

        Iterable<Filme> filmes = filmeService.findAll();

        assertThat(filmes).isNotNull();
        assertThat(filmes).asList().isNotNull();
    }

    @Test
    @DisplayName("Deve atualizar um filme")
    public void shouldUpdateFilme(){
        Filme filme = createFilme();

        when(filmeRepository.findById(Mockito.anyString())).thenReturn(java.util.Optional.of(filme));

        when(filmeRepository.save(filme)).thenReturn(filme);

        Filme filmeAtualizado = filmeService.update(filme);

        assertThat(filmeAtualizado.getId()).isNotNull();
        assertThat(filmeAtualizado.getTitulo()).isEqualTo(filme.getTitulo());
        assertThat(filmeAtualizado.getTituloOriginal()).isEqualTo(filme.getTituloOriginal());
        assertThat(filmeAtualizado.getDiretor()).isEqualTo(filme.getDiretor());
        assertThat(filmeAtualizado.getGenero()).isEqualTo(filme.getGenero());
        assertThat(filmeAtualizado.getSinopse()).isEqualTo(filme.getSinopse());
        assertThat(filmeAtualizado.getDuracao()).isEqualTo(filme.getDuracao());
    }

    @Test
    @DisplayName("Deve deletar um filme")
    public void shouldDeleteFilme() {
        Filme filme = createFilme();

        when(filmeRepository.findById(Mockito.anyString())).thenReturn(java.util.Optional.of(filme));

        filmeService.deleteById(filme.getId());

        verify(filmeRepository, times(1)).deleteById(filme.getId());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar um filme inexistente")
    public void shouldThrowExceptionWhenDeleteFilmeInexistente(){

        when(filmeRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> filmeService.deleteById("1"));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar um filme inexistente, ou com dados inválidos")
    public void shouldThrowExceptionWhenUpdateFilmeInexistente(){

        when(filmeRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> filmeService.update(new Filme()));
    }

}




