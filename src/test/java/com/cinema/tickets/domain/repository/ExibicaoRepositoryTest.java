package com.cinema.tickets.domain.repository;

import com.cinema.tickets.domain.collection.Exibicao;
import com.cinema.tickets.domain.collection.Filme;
import com.cinema.tickets.domain.collection.Horarios;
import com.cinema.tickets.domain.collection.Sala;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class ExibicaoRepositoryTest {

    @Autowired
    private ExibicaoRepository exibicaoRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private SalaRepository salaRepository;

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
    @DisplayName("Deve salvar uma exibição")
    public void save() {
        Exibicao exibicao = createExibicao();

        this.exibicaoRepository.save(exibicao);

        assertThat(exibicao.getId()).isNotNull();
        assertThat(exibicao.getDataExibicao()).isEqualTo(LocalDate.of(2022, 1, 22));
        assertThat(exibicao.getFilme().getId()).isEqualTo("1");
        assertThat(exibicao.getSala().getId()).isEqualTo("1");
        assertThat(exibicao.getHorarios().get(0).getHorario()).isEqualTo(LocalTime.of(14, 30));
    }

    @Test
    @DisplayName("Deve buscar uma exibição")
    public void findById() {
        Exibicao exibicao = createExibicao();

        this.exibicaoRepository.save(exibicao);

        assertThat(this.exibicaoRepository.findById(exibicao.getId())).isNotNull();
    }

}

