package com.cinema.tickets.domain.strategy;

import com.cinema.tickets.domain.collection.Filme;
import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.exception.NotFoundException;
import com.cinema.tickets.domain.repository.FilmeRepository;
import com.cinema.tickets.domain.strategy.impl.FilmeStrategyImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class FilmeStrategyTest {

    FilmeStrategy filmeStrategy;

    @MockBean
    FilmeRepository filmeRepository;

    @BeforeEach
    public void setUp(){
        filmeStrategy = new FilmeStrategyImpl(filmeRepository);
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
    @DisplayName("Deve lançar exceção ao tentar cadastrar um filme com o mesmo título")
    public void shouldThrowExceptionWhenSave(){
        Filme filme = createFilme();

        when(filmeRepository.findByTitulo(anyString())).thenReturn(Optional.of(filme));

        assertThrows(BusinessException.class, () -> filmeStrategy.validateFilme(filme));
    }

    @Test
    @DisplayName("Deve validar um filme")
    public void shouldValidateFilme(){
        Filme filme = createFilme();

        when(filmeRepository.findByTitulo(anyString())).thenReturn(Optional.empty());

        filmeStrategy.validateFilme(filme);
    }


    @Test
    @DisplayName("Deve lançar exceção ao tentar cadastrar um filme com id inexistente")
    public void shouldThrowExceptionWhenSaveWithId(){
       String id = "1";

        when(filmeRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> filmeStrategy.validateId(id));
    }

    @Test
    @DisplayName("Deve validar um id de filme")
    public void shouldValidateFilmeId(){
        Filme filme = createFilme();

        when(filmeRepository.findById(anyString())).thenReturn(Optional.of(filme));

        filmeStrategy.validateId(filme.getId());
    }

}
