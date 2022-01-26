package com.cinema.tickets.domain.service;

import com.cinema.tickets.domain.collection.Ingresso;

import java.util.List;
import java.util.Optional;

public interface IngressoService {

    Ingresso save (Ingresso ingresso);

    Optional<Ingresso> findById (String id);

    void delete (String id);

    List<Ingresso> findAll();


}
