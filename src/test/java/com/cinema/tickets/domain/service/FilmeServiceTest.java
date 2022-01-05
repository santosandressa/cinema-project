package com.cinema.tickets.domain.service;

import com.cinema.tickets.domain.collection.Filme;
import com.cinema.tickets.domain.repository.FilmeRepository;
import com.cinema.tickets.domain.service.impl.FilmeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class FilmeServiceTest {

   FilmeService filmeService;

    @MockBean
    FilmeRepository filmeRepository;

    @BeforeEach
    public void setUp() {
        filmeService = new FilmeServiceImpl(filmeRepository);
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

}


