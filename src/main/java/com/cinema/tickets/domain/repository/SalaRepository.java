package com.cinema.tickets.domain.repository;

import com.cinema.tickets.domain.collection.Sala;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface  SalaRepository extends MongoRepository<Sala, String> {

    Optional<Sala> findByNumSala(Integer numSala);

    Boolean existsByNumSala(Integer numSala);

}
