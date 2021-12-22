package com.cinema.tickets.domain.repository;

import com.cinema.tickets.domain.collection.Filme;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmeRepository extends MongoRepository<Filme, String> {

}
