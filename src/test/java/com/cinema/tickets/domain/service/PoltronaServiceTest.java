package com.cinema.tickets.domain.service;

import com.cinema.tickets.common.config.domain.collection.Poltrona;
import com.cinema.tickets.common.config.domain.repository.PoltronaRepository;
import com.cinema.tickets.common.config.domain.service.PoltronaService;
import com.cinema.tickets.common.config.domain.service.impl.PoltronaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PoltronaServiceTest {

    PoltronaService poltronaService;

    @MockBean
    PoltronaRepository poltronaRepository;

    @BeforeEach
    public void setUp() {
        poltronaService = new PoltronaServiceImpl(poltronaRepository);
    }

    private Poltrona createPoltrona() {
        Poltrona poltrona = new Poltrona();
        poltrona.setId("1");
        poltrona.setFileira("A");
        poltrona.setCadeira("A8");
        poltrona.setStatus("DISPONIVEL");
        return poltrona;
    }

    @Test
    @DisplayName("Deve salvar uma poltrona")
    public void savePoltrona() {
        Poltrona poltrona = createPoltrona();

        when(poltronaRepository.existsByFileiraAndCadeira(poltrona.getFileira(), poltrona.getCadeira())).thenReturn(false);

        when(poltronaRepository.save(poltrona)).thenReturn(poltrona);

        poltrona = poltronaService.save(poltrona);

        assertThat(poltrona.getId()).isNotNull();
        assertThat(poltrona.getFileira()).isEqualTo(poltrona.getFileira());
        assertThat(poltrona.getCadeira()).isEqualTo(poltrona.getCadeira());
        assertThat(poltrona.getStatus()).isEqualTo(poltrona.getStatus());
    }
}
