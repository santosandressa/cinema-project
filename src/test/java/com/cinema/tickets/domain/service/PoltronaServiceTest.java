package com.cinema.tickets.domain.service;

import com.cinema.tickets.domain.collection.Poltrona;
import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.exception.NotFoundException;
import com.cinema.tickets.domain.repository.PoltronaRepository;
import com.cinema.tickets.domain.service.impl.PoltronaServiceImpl;
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
        poltrona.setCadeira("A1");
        poltrona.setFileira("A");
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

    @Test
    @DisplayName("Deve lançar exception ao salvar uma poltrona nula")
    public void shouldThrowExceptionSavePoltronaNull(){

        Poltrona poltrona = new Poltrona();

        if (poltrona.getCadeira() == null && poltrona.getFileira() == null ) {
            assertThrows(BusinessException.class, () -> poltronaService.save(poltrona));
        }
    }

    @Test
    @DisplayName("Deve lançar exception ao salvar uma poltrona com cadeira e fileira que já existem")
    public void shouldThrowExceptionSavePoltronaExists(){

        Poltrona poltrona = createPoltrona();

        when(poltronaRepository.existsByFileiraAndCadeira(poltrona.getFileira(), poltrona.getCadeira())).thenReturn(true);

        assertThrows(BusinessException.class, () -> poltronaService.save(poltrona));
    }


    @Test
    @DisplayName("Deve procurar uma poltrona pelo id")
    public void shoulFindPoltroById(){
        Poltrona poltrona = createPoltrona();

        when(poltronaRepository.findById(Mockito.anyString())).thenReturn(java.util.Optional.of(poltrona));

        Optional<Poltrona> poltronaId = poltronaService.findById(poltrona.getId());

        assertThat(poltronaId.isPresent()).isTrue();
        assertThat(poltronaId.get().getId()).isNotNull();
        assertThat(poltronaId.get().getFileira()).isEqualTo(poltrona.getFileira());
        assertThat(poltronaId.get().getCadeira()).isEqualTo(poltrona.getCadeira());
        assertThat(poltronaId.get().getStatus()).isEqualTo(poltrona.getStatus());
    }

    @Test
    @DisplayName("Deve listar todas as poltronas")
    public void shouldFindAll(){

        Poltrona poltrona = createPoltrona();

        when(poltronaRepository.findAll()).thenReturn(List.of(poltrona));

        List<Poltrona> poltronas = poltronaService.findAll();

        assertThat(poltronas).isNotNull();
        assertThat(poltronas.size()).isEqualTo(1);
        assertThat(poltronas).asList().isNotNull();
    }


    @Test
    @DisplayName("Deve atualizar uma poltrona")
    public void shouldUpdate(){
        Poltrona poltrona = createPoltrona();

        when(poltronaRepository.findById(Mockito.anyString())).thenReturn(java.util.Optional.of(poltrona));

        when(poltronaRepository.save(poltrona)).thenReturn(poltrona);

        poltrona = poltronaService.update(poltrona);

        assertThat(poltrona.getId()).isNotNull();
        assertThat(poltrona.getFileira()).isEqualTo(poltrona.getFileira());
        assertThat(poltrona.getCadeira()).isEqualTo(poltrona.getCadeira());
        assertThat(poltrona.getStatus()).isEqualTo(poltrona.getStatus());
    }

    @Test
    @DisplayName("Deve lançar exception ao atualizar uma poltrona inexistente, ou com dados inválidos")
    public void shouldThrowExceptionUpdate(){

        Poltrona poltrona = new Poltrona();

        when(poltronaRepository.findById(Mockito.anyString())).thenReturn(java.util.Optional.empty());

        Optional<Poltrona> poltronaId = poltronaService.findById(poltrona.getId());

        if(poltronaId.isEmpty() || poltrona.getId() == null){
            assertThrows(NotFoundException.class, () -> poltronaService.update(poltrona));
        }

    }
}
