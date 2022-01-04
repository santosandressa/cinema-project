package com.cinema.tickets.domain.repository;

import com.cinema.tickets.common.config.domain.collection.Poltrona;
import com.cinema.tickets.common.config.domain.repository.PoltronaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class PoltronaRepositoryTest {

    @Autowired
    PoltronaRepository poltronaRepository;

    private Poltrona createPoltrona(){
        Poltrona poltrona = new Poltrona();
        poltrona.setId("1");
        poltrona.setCadeira("K");
        poltrona.setFileira("K2");
        poltrona.setStatus("RESERVADA");
        return poltrona;
    }

    @Test
    @DisplayName("Deve obter uma poltrona pelo id")
    public void findPoltronaById(){
        Poltrona poltrona = createPoltrona();

        poltronaRepository.save(poltrona);

        Optional<Poltrona> poltronaOptional = poltronaRepository.findById(poltrona.getId());

        assertThat(poltronaOptional.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Deve retornar false quando não encontrar uma poltrona pelo id")
    public void findPoltronaByIdNotFound(){

        Optional<Poltrona> poltronaOptional = poltronaRepository.findById("4");

        assertThat(poltronaOptional.isPresent()).isFalse();
    }

}
