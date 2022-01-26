package com.cinema.tickets.domain.repository;

import com.cinema.tickets.domain.collection.Email;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends MongoRepository<Email, String> {

}
