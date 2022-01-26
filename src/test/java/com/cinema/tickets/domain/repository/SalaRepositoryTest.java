package com.cinema.tickets.domain.repository;

import com.cinema.tickets.domain.collection.Sala;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class SalaRepositoryTest {

    @Autowired
    SalaRepository salaRepository;

    private Sala createSala(){
        Sala sala = new Sala();
        sala.setId("1");
        sala.setNumSala(1);
        sala.setSala3D(true);
        sala.setCapacidade(216);

        return sala;
    }

    @Test
    @DisplayName("Deve encontrar uma sala pelo id")
    public void findSalaById(){
        Sala sala = createSala();
        salaRepository.save(sala);

        Optional<Sala> salaOptional = salaRepository.findById(sala.getId());

        assertThat(salaOptional.isPresent()).isTrue();
    }


    @Test
    @DisplayName("Deve encontrar uma sala pelo numero 3D")
    public void findSalaByIdNotFound(){
        Optional<Sala> salaOptional = salaRepository.findById("6");

        assertThat(salaOptional.isPresent()).isFalse();
    }
}
