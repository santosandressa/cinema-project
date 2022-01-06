package com.cinema.tickets.domain.service;

import com.cinema.tickets.domain.collection.Poltrona;

import java.util.List;
import java.util.Optional;

public interface PoltronaService {

    Poltrona save(Poltrona poltrona);

    Optional<Poltrona> findById(String id);

    List<Poltrona> findAll();

    Poltrona update(Poltrona poltrona);

}
