package com.cinema.tickets.common.config.domain.repository;

import com.cinema.tickets.common.config.domain.collection.Poltrona;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PoltronaRepository extends MongoRepository<Poltrona, String> {

    Boolean existsByFileiraAndCadeira(String fileira, String cadeira);
}
