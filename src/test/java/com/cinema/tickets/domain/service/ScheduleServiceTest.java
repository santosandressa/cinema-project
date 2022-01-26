package com.cinema.tickets.domain.service;

import com.cinema.tickets.domain.collection.Exibicao;
import com.cinema.tickets.domain.collection.Filme;
import com.cinema.tickets.domain.collection.Horarios;
import com.cinema.tickets.domain.collection.Sala;
import com.cinema.tickets.domain.repository.ExibicaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static java.util.concurrent.TimeUnit.MINUTES;
import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class ScheduleServiceTest {

    @SpyBean
    ScheduleService scheduleService;

    @MockBean
    ExibicaoRepository exibicaoRepository;



    @BeforeEach
    public void setUp() {
        scheduleService = new ScheduleService(exibicaoRepository);
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
    @DisplayName("Deve deletar exibicao fora de cartaz")
    public void shouldDelete(){
        when(exibicaoRepository.findAll()).thenReturn(List.of(createExibicao()));

        scheduleService.deleteExpiredExibition();

        await().atMost(10, MINUTES).untilAsserted(() -> verify(exibicaoRepository).deleteById(anyString()));
    }

}
