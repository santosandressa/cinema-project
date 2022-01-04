package com.cinema.tickets.common.config.domain.repository;

import com.cinema.tickets.common.config.domain.collection.Sala;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  SalaRepository extends MongoRepository<Sala, String> {
}
