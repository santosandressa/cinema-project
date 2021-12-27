package com.cinema.tickets.domain.strategy;

import com.cinema.tickets.domain.collection.Filme;

public interface FilmeStrategy {

    void validateFilme(Filme filme);
}
