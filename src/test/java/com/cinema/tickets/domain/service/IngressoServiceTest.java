package com.cinema.tickets.domain.service;

import com.cinema.tickets.domain.collection.*;
import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.exception.NotFoundException;
import com.cinema.tickets.domain.repository.ExibicaoRepository;
import com.cinema.tickets.domain.repository.IngressoRepository;
import com.cinema.tickets.domain.service.impl.IngressoServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class IngressoServiceTest {

    IngressoService ingressoService;

    @MockBean
    IngressoRepository ingressoRepository;

    @MockBean
    ExibicaoRepository exibicaoRepository;

    @BeforeEach
    void setUp() {
        ingressoService = new IngressoServiceImpl(ingressoRepository, exibicaoRepository);
    }

    private Ingresso createIngresso(){
        Ingresso ingresso = new Ingresso();
        ingresso.setId("1");
        ingresso.setValor(ingresso.getValor());
        ingresso.setMeiaEntrada(false);
        ingresso.setQuantidade(1);


        Exibicao exibicao = new Exibicao();
        exibicao.setId("1");
        exibicao.setDataExibicao(LocalDate.of(2022, 1, 22));
        Horarios horarios = new Horarios();
        horarios.setHorario(LocalTime.of(14, 30));

        exibicao.setHorarios(List.of(horarios));

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

    @Test
    @DisplayName("Deve salvar um ingresso")
    void save() {
        Ingresso ingresso = createIngresso();

        if (ingresso.getMeiaEntrada()) {

            ingresso.setValor(30.0 / 2);
        } else {
            ingresso.setValor(30.0);
        }

        ingresso.setValorTotal(ingresso.getQuantidade() * ingresso.getValor());

        when(exibicaoRepository.findById(ingresso.getExibicao().getId())).thenReturn(Optional.of(ingresso.getExibicao()));

        ingresso.setExibicao(ingresso.getExibicao());

        when(ingressoRepository.save(ingresso)).thenReturn(ingresso);

       ingresso = ingressoService.save(ingresso);


       assertThat(ingresso.getId()).isNotNull();
       assertThat(ingresso.getValorTotal()).isNotNull();
       assertThat(ingresso.getValorTotal()).isEqualTo(ingresso.getValorTotal());
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar salvar um ingresso com quantidade acima do permitido")
    void saveInvalid() {
        Ingresso ingresso = createIngresso();
        ingresso.setQuantidade(6);

        when(exibicaoRepository.findById(ingresso.getExibicao().getId())).thenReturn(Optional.of(ingresso.getExibicao()));

        ingresso.setExibicao(ingresso.getExibicao());

        when(ingressoRepository.save(ingresso)).thenReturn(ingresso);

        assertThrows(BusinessException.class, () -> ingressoService.save(ingresso));
    }

    @Test
    @DisplayName("Deve buscar um ingresso pelo id")
    void findById() {
        Ingresso ingresso = createIngresso();

        when(ingressoRepository.findById(ingresso.getId())).thenReturn(Optional.of(ingresso));

        Optional<Ingresso> ingressoReturn = ingressoService.findById(ingresso.getId());

        assertThat(ingressoReturn.get().getId()).isEqualTo(ingresso.getId());
        assertThat(ingressoReturn.get().getValorTotal()).isEqualTo(ingresso.getValorTotal());
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar buscar um ingresso pelo id inexistente")
    void findByIdInvalid() {
        Ingresso ingresso = createIngresso();

        when(ingressoRepository.findById(ingresso.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> ingressoService.findById(ingresso.getId()));
    }

    @Test
    @DisplayName("Deve deletar um ingresso")
    void delete() {
        Ingresso ingresso = createIngresso();

        when(ingressoRepository.findById(ingresso.getId())).thenReturn(Optional.of(ingresso));

        ingressoService.delete(ingresso.getId());
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar deletar um ingresso inexistente")
    void deleteInvalid() {
        Ingresso ingresso = createIngresso();

        when(ingressoRepository.findById(ingresso.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> ingressoService.delete(ingresso.getId()));
    }

    @Test
    @DisplayName("Deve buscar todos os ingressos")
    void findAll() {
        Ingresso ingresso = createIngresso();

        when(ingressoRepository.findAll()).thenReturn(List.of(ingresso));

        List<Ingresso> ingressos = ingressoService.findAll();

        assertThat(ingressos.size()).isEqualTo(1);
        assertThat(ingressos.get(0).getId()).isEqualTo(ingresso.getId());
        assertThat(ingressos.get(0).getValorTotal()).isEqualTo(ingresso.getValorTotal());
    }

}
