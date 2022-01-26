package com.cinema.tickets.domain.service;

import com.cinema.tickets.domain.collection.Sala;
import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.exception.NotFoundException;
import com.cinema.tickets.domain.repository.SalaRepository;
import com.cinema.tickets.domain.service.impl.SalaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

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
public class SalaServiceTest {

    SalaService salaService;

    @MockBean
    SalaRepository salaRepository;



    @BeforeEach
    public void setUp() {
        salaService = new SalaServiceImpl(salaRepository);
    }

    private Sala createSala(){
        Sala sala = new Sala();
        sala.setId("1");
        sala.setNumSala(1);
        sala.setSala3D(true);
        sala.setCapacidade(216);


        return sala;
    }

    @Test
    @DisplayName("Deve salvar uma sala")
    public void saveSala(){
        Sala sala = createSala();

        when(salaRepository.findByNumSala(sala.getNumSala())).thenReturn(Optional.empty());

        when(salaRepository.save(sala)).thenReturn(sala);

        sala = salaService.save(sala);

        assertThat(sala.getId()).isNotNull();
        assertThat(sala.getNumSala()).isEqualTo(1);
        assertThat(sala.getSala3D()).isTrue();
        assertThat(sala.getCapacidade()).isEqualTo(216);
    }

    @Test
    @DisplayName("Deve lançar exception ao salvar uma sala com numero de sala já existente")
    public void shouldThrowExceptionWhenSaveSalaWithExistingNumSala(){
        Sala sala = createSala();

        when(salaRepository.existsByNumSala(sala.getNumSala())).thenReturn(true);

        assertThrows(BusinessException.class, () -> salaService.save(sala));
    }

    @Test
    @DisplayName("Deve listar todas as salas")
    public void shouldListAllSala() {
        Sala sala = createSala();

        when(salaRepository.findAll()).thenReturn(List.of(sala));

        List<Sala> salas = salaService.findAll();

        assertThat(salaService.findAll()).isNotNull();
        assertThat(salas.size()).isEqualTo(1);
        assertThat(salas).asList().isNotNull();
    }


    @Test
    @DisplayName("Deve listar uma sala por id")
    public void shouldListSalaById() {
        Sala sala = createSala();

        when(salaRepository.findById(sala.getId())).thenReturn(Optional.of(sala));

        Optional<Sala> salaOptional = salaService.findById(sala.getId());

        assertThat(salaOptional.isPresent()).isTrue();
        assertThat(salaOptional.get().getId()).isEqualTo(sala.getId());
        assertThat(salaOptional.get().getNumSala()).isEqualTo(sala.getNumSala());
        assertThat(salaOptional.get().getSala3D()).isEqualTo(sala.getSala3D());
        assertThat(salaOptional.get().getCapacidade()).isEqualTo(sala.getCapacidade());
    }

    @Test
    @DisplayName("Deve lançar exception ao listar uma sala por id")
    public void shouldThrowExceptionWhenListSalaById() {
        Sala sala = createSala();

        when(salaRepository.findById(sala.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> salaService.findById(sala.getId()));
    }


    @Test
    @DisplayName("Deve atualizar uma sala")
    public void shouldUpdateSala() {
        Sala sala = createSala();

        when(salaRepository.findById(sala.getId())).thenReturn(Optional.of(sala));

        when(salaRepository.save(sala)).thenReturn(sala);

        sala = salaService.update(sala);

        assertThat(sala.getId()).isNotNull();
        assertThat(sala.getNumSala()).isEqualTo(1);
        assertThat(sala.getSala3D()).isTrue();
        assertThat(sala.getCapacidade()).isEqualTo(216);
    }

    @Test
    @DisplayName("Deve lançar exception ao atualizar uma sala não existente, ou com dados inválidos")
    public void shouldThrowExceptionWhenUpdateSalaWithInvalidData() {
        Sala sala = new Sala();

        assertThrows(BusinessException.class, () -> salaService.update(sala));
    }


}
