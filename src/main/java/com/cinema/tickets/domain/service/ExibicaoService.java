package com.cinema.tickets.domain.service;

import com.cinema.tickets.domain.collection.Exibicao;

import java.util.List;
import java.util.Optional;

public interface ExibicaoService {

    List<Exibicao> getAll();

    Exibicao save(Exibicao exibicao);

    Optional<Exibicao> getById(String id);

    void delete(String id);

    Exibicao update(Exibicao exibicao);

}
