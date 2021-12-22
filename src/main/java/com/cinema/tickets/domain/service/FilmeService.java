package com.cinema.tickets.domain.service;

import com.cinema.tickets.domain.collection.Filme;

import java.util.List;
import java.util.Optional;

public interface FilmeService {

    Filme save(Filme filme);

    List<Filme> findAll();

    Optional<Filme> findById(String id);

    Filme update(Filme filme);

    void deleteById(String id);
}
