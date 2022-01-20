package com.cinema.tickets.domain.service;

import com.cinema.tickets.domain.collection.Exibicao;
import com.cinema.tickets.domain.collection.Filme;
import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.exception.NotFoundException;
import com.cinema.tickets.domain.repository.ExibicaoRepository;
import com.cinema.tickets.domain.repository.FilmeRepository;
import com.cinema.tickets.domain.service.impl.ExibicaoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ExibicaoServiceTest {

    ExibicaoService exibicaoService;

    @MockBean
    ExibicaoRepository exibicaoRepository;

    @MockBean
    FilmeRepository filmeRepository;


    @BeforeEach
    public void setUp(){

        exibicaoService = new ExibicaoServiceImpl(exibicaoRepository, filmeRepository);
    }

    private Exibicao createExibicao(){
        Exibicao exibicao = new Exibicao();
        exibicao.setId("1");
        exibicao.setDataExibicao(LocalDate.of(2022, 1, 22));
        exibicao.setFilmes(exibicao.getFilmes());
        return exibicao;
    }

    @Test
    @DisplayName("Deve listar todas as exibições")
    public void shouldListAll() {
        Exibicao exibicao = createExibicao();

        when(exibicaoRepository.findAll()).thenReturn(List.of(exibicao));

        List<Exibicao> exibicoes = exibicaoService.getAll();

        assert(exibicoes.size() == 1);
        assertThat(exibicoes).isNotNull();
    }

    @Test
    @DisplayName("Deve salvar uma exibição")
    public void shouldSaveExibicao(){
        Exibicao exibicao = createExibicao();
        List<Filme> filmes = filmeRepository.findAll();

        when(filmeRepository.findAll()).thenReturn(filmes);

        when(exibicaoRepository.existsByDataExibicao(exibicao.getDataExibicao())).thenReturn(false);

        exibicao.setFilmes(filmes);

        when(exibicaoRepository.save(exibicao)).thenReturn(exibicao);

        Exibicao exibicaoSalva = exibicaoService.save(exibicao);

        assertThat(exibicaoSalva.getId()).isNotNull();
        assertThat(exibicaoSalva.getDataExibicao()).isEqualTo(exibicao.getDataExibicao());
        assertThat(exibicaoSalva.getFilmes()).isEqualTo(exibicao.getFilmes());
    }

    @Test
    @DisplayName("Deve lançar execeção ao tentar salvar uma exibição com data de exibição já existente")
    public void shouldThrowExceptionWhenSaveExibicaoWithExistingDate(){
        Exibicao exbicao = createExibicao();

        when(exibicaoRepository.existsByDataExibicao(exbicao.getDataExibicao())).thenReturn(true);

        assertThrows(BusinessException.class, () -> exibicaoService.save(exbicao));
    }

    @Test
    @DisplayName("Deve buscar uma exibição por id")
    public void shouldFindExibicaoById(){
        Exibicao exibicao = createExibicao();

        when(exibicaoRepository.findById(anyString())).thenReturn(Optional.of(exibicao));

        Optional<Exibicao> exibicaoOptional = exibicaoService.getById(exibicao.getId());

        assertThat(exibicaoOptional.isPresent()).isTrue();
        assertThat(exibicaoOptional.get().getId()).isEqualTo(exibicao.getId());
        assertThat(exibicaoOptional.get().getDataExibicao()).isEqualTo(exibicao.getDataExibicao());
        assertThat(exibicaoOptional.get().getFilmes()).isEqualTo(exibicao.getFilmes());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar buscar uma exibição por id inexistente")
    public void shouldThrowExceptionWhenFindExibicaoById(){
        when(exibicaoRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> exibicaoService.getById("1"));
    }

    @Test
    @DisplayName("Deve deletar uma exibição por id")
    public void shouldDeleteExibicaoById(){
        Exibicao exibicao = createExibicao();

        when(exibicaoRepository.findById(anyString())).thenReturn(Optional.of(exibicao));

        exibicaoService.delete(exibicao.getId());

        verify(exibicaoRepository, times(1)).deleteById(anyString());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar deletar uma exibição por id inexistente")
    public void shouldThrowExceptionWhenDeleteExibicaoById(){
        when(exibicaoRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> exibicaoService.delete("1"));
    }

    @Test
    @DisplayName("Deve atualizar uma exibição")
    public void shouldUpdateExibicao(){
        Exibicao exibicao = createExibicao();

        when(exibicaoRepository.findById(anyString())).thenReturn(Optional.of(exibicao));

        when(exibicaoRepository.save(exibicao)).thenReturn(exibicao);

        Exibicao exibicaoAtualizada = exibicaoService.update(exibicao);

        assertThat(exibicaoAtualizada.getId()).isNotNull();
        assertThat(exibicaoAtualizada.getDataExibicao()).isEqualTo(exibicao.getDataExibicao());
        assertThat(exibicaoAtualizada.getFilmes()).isEqualTo(exibicao.getFilmes());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar atualizar uma exibição por id inexistente")
    public void shouldThrowExceptionWhenUpdateExibicaoById(){
        when(exibicaoRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> exibicaoService.update(createExibicao()));
    }

}
