package com.cinema.tickets.domain.repository;

import com.cinema.tickets.domain.collection.Poltrona;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@ImportAutoConfiguration( FeignAutoConfiguration.class)
@ActiveProfiles("test")
public class PoltronaRepositoryTest {

    @Autowired
    PoltronaRepository poltronaRepository;

    private Poltrona createPoltrona() {
        Poltrona poltrona = new Poltrona();
        poltrona.setId("1");
        poltrona.setCadeira("A1");
        poltrona.setFileira("A");
        poltrona.setReservado(false);
        poltrona.setEspeciais("Normal");
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
