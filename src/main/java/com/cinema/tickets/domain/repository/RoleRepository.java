package com.cinema.tickets.domain.repository;

import com.cinema.tickets.domain.collection.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

    Role findByNome(String nome);
}
