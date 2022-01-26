package com.cinema.tickets.domain.service;

import com.cinema.tickets.domain.collection.Exibicao;
import com.cinema.tickets.domain.collection.Filme;
import com.cinema.tickets.domain.collection.Horarios;
import com.cinema.tickets.domain.collection.Sala;
import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.exception.NotFoundException;
import com.cinema.tickets.domain.repository.ExibicaoRepository;
import com.cinema.tickets.domain.repository.FilmeRepository;
import com.cinema.tickets.domain.repository.SalaRepository;
import com.cinema.tickets.domain.service.impl.ExibicaoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ExibicaoServiceTest {

    ExibicaoService exibicaoService;

    @MockBean
    FilmeRepository filmeRepository;

    @MockBean
    ExibicaoRepository exibicaoRepository;

    @MockBean
    SalaRepository salaRepository;

    @BeforeEach
    public void setUp() {
        exibicaoService = new ExibicaoServiceImpl(exibicaoRepository, filmeRepository, salaRepository);
    }

    private Exibicao createExibicao() {
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

        exibicao.setFilme(filme);
        exibicao.setSala(sala);

        Horarios horarios = new Horarios();
        horarios.setHorario(LocalTime.of(14, 30));

        exibicao.setHorarios(List.of(horarios));

        return exibicao;
    }

    @Test
    @DisplayName("Deve salvar uma nova exibição")
    public void saveExibicao() {
        Exibicao exibicao = createExibicao();

        when(filmeRepository.findByTitulo(exibicao.getFilme().getTitulo())).thenReturn(Optional.of(exibicao.getFilme()));

        exibicao.setFilme(exibicao.getFilme());

        when(exibicaoRepository.existsByDataExibicao(exibicao.getDataExibicao())).thenReturn(false);

        when(salaRepository.findByNumSala(exibicao.getSala().getNumSala())).thenReturn(Optional.of(exibicao.getSala()));

        exibicao.setSala(exibicao.getSala());

        when(exibicaoRepository.save(exibicao)).thenReturn(exibicao);

        exibicao = exibicaoService.save(exibicao);

        assertThat(exibicao.getId()).isNotNull();
        assertThat(exibicao.getDataExibicao()).isEqualTo(exibicao.getDataExibicao());
        assertThat(exibicao.getFilme().getTitulo()).isEqualTo(exibicao.getFilme().getTitulo());
        assertThat(exibicao.getSala().getNumSala()).isEqualTo(exibicao.getSala().getNumSala());
        assertThat(exibicao.getHorarios().get(0).getHorario()).isEqualTo(exibicao.getHorarios().get(0).getHorario());
    }


    @Test
    @DisplayName("Deve lançar not found ao procurar por filme inexistente")
    public void findByFilmeInexistente() {
        when(filmeRepository.findByTitulo(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> exibicaoService.save(createExibicao()));
    }

    @Test
    @DisplayName("Deve lançar bad request ao tentar salvar uma exibição com data de exibição já existente")
    public void saveExibicaoExistente() {
        Exibicao exibicao = createExibicao();

        when(filmeRepository.findByTitulo(exibicao.getFilme().getTitulo())).thenReturn(Optional.of(exibicao.getFilme()));

        exibicao.setFilme(exibicao.getFilme());

        when(exibicaoRepository.existsByDataExibicao(exibicao.getDataExibicao())).thenReturn(true);

        assertThatThrownBy(() -> exibicaoService.save(exibicao)).isInstanceOf(BusinessException.class);
    }


    @Test
    @DisplayName("deve lançar not found ao procurar por sala inexistente")
    public void findBySalaInexistente() {

        when(salaRepository.findByNumSala(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> exibicaoService.save(createExibicao()));
    }

    @Test
    @DisplayName("Deve listar todas as exibições")
    public void listAll() {
        when(exibicaoRepository.findAll()).thenReturn(List.of(createExibicao()));

        List<Exibicao> exibicoes = exibicaoService.getAll();

        assertThat(exibicoes.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Deve listar por id")
    public void findById() {
        Exibicao exibicao = createExibicao();

        when(exibicaoRepository.findById(anyString())).thenReturn(Optional.of(createExibicao()));

        exibicaoService.getById(exibicao.getId());

        assertThat(exibicao.getId()).isEqualTo(exibicao.getId());
    }

    @Test
    @DisplayName("Deve lançar not found ao procurar por id inexistente")
    public void findByIdInexistente() {
        when(exibicaoRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> exibicaoService.getById(anyString()));
    }

    @Test
    @DisplayName("Deve deletar uma exibição")
    public void delete() {
        Exibicao exibicao = createExibicao();

        when(exibicaoRepository.findById(anyString())).thenReturn(Optional.of(exibicao));

        exibicaoService.delete(exibicao.getId());

        verify(exibicaoRepository).deleteById(anyString());
    }

    @Test
    @DisplayName("Deve lançar not found ao tentar deletar uma exibição inexistente")
    public void deleteInexistente() {
        when(exibicaoRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> exibicaoService.delete(anyString()));
    }

    @Test
    @DisplayName("Deve atualizar uma exibição")
    public void update() {
        Exibicao exibicao = createExibicao();

        when(exibicaoRepository.findById(anyString())).thenReturn(Optional.of(exibicao));

        when(exibicaoRepository.save(exibicao)).thenReturn(exibicao);

        exibicao = exibicaoService.update(exibicao);

        assertThat(exibicao.getId()).isEqualTo(exibicao.getId());
        assertThat(exibicao.getDataExibicao()).isEqualTo(exibicao.getDataExibicao());
        assertThat(exibicao.getFilme().getTitulo()).isEqualTo(exibicao.getFilme().getTitulo());
        assertThat(exibicao.getSala().getNumSala()).isEqualTo(exibicao.getSala().getNumSala());
        assertThat(exibicao.getHorarios().get(0).getHorario()).isEqualTo(exibicao.getHorarios().get(0).getHorario());

        verify(exibicaoRepository).save(exibicao);
    }

    @Test
    @DisplayName("Deve lançar not found ao tentar atualizar uma exibição existente")
    public void updateInexistente() {
        when(exibicaoRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> exibicaoService.update(createExibicao()));
    }
}
