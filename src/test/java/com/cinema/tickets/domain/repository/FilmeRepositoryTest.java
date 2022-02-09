package com.cinema.tickets.domain.repository;

import com.cinema.tickets.domain.collection.Filme;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@ImportAutoConfiguration( FeignAutoConfiguration.class)
@ActiveProfiles("test")
public class FilmeRepositoryTest {

    @Autowired
    FilmeRepository filmeRepository;

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
    @DisplayName("Deve retornar verdadeiro quando existir um filme com o id informado")
    public void findById_ShouldReturnTrue_WhenFilmeExists() {

        Filme filme = createFilme();
        String id = "1";

        filmeRepository.save(filme);

        Optional<Filme> foundFilme = filmeRepository.findById(id);

        assertThat(foundFilme.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Deve retornar falso quando não existir um filme com o id informado")
    public void findById_ShouldReturnFalse_WhenFilmeNotExists() {

        String id = "9";

        Optional<Filme> notFoundFilme = filmeRepository.findById(id);

        assertThat(notFoundFilme.isPresent()).isFalse();
    }

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir um filme com o titulo informado")
    public void findByTitulo_ShouldReturnTrue_WhenFilmeExists() {

        Filme filme = createFilme();
        String titulo = "Percy Jackson e o mar de monstros";

        filmeRepository.save(filme);

        Optional<Filme> foundFilme = filmeRepository.findByTitulo(titulo);

        assertThat(foundFilme.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Deve retornar falso quando não existir um filme com o titulo informado")
    public void findByTitulo_ShouldReturnFalse_WhenFilmeNotExists() {

        String titulo = "Coringa";

        Optional<Filme> notFoundFilme = filmeRepository.findByTitulo(titulo);

        assertThat(notFoundFilme.isPresent()).isFalse();
    }


}
