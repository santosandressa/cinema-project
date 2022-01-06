package com.cinema.tickets.domain.service;

import com.cinema.tickets.domain.collection.Sala;

import java.util.List;
import java.util.Optional;

public interface SalaService  {

    Sala save(Sala sala);

    List<Sala> findAll();

    Optional<Sala> findById(String id);

    Sala update(Sala sala);
}
