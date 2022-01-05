package com.cinema.tickets.domain.repository;

import com.cinema.tickets.domain.collection.Poltrona;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PoltronaRepository extends MongoRepository<Poltrona, String> {

    Boolean existsByFileiraAndCadeira(String fileira, String cadeira);


}
