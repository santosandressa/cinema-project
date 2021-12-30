package com.cinema.tickets.domain.repository;

import com.cinema.tickets.domain.collection.Poltrona;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PoltronaRepository extends MongoRepository<Poltrona, String> {

}
