package com.cinema.tickets.domain.repository;

import com.cinema.tickets.domain.collection.Ingresso;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngressoRepository extends MongoRepository<Ingresso, String> {

}