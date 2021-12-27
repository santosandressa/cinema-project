package com.cinema.tickets.domain.strategy.impl;

import com.cinema.tickets.domain.collection.Filme;
import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.repository.FilmeRepository;
import com.cinema.tickets.domain.strategy.FilmeStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FilmeStrategyImpl implements FilmeStrategy {

    @Autowired
    FilmeRepository filmeRepository;

    @Override
    public void validateFilme(Filme filme) {
        Boolean filmeExistsByNome = filmeRepository.existsByTitulo(filme.getTitulo());

        if (filmeExistsByNome) {
            throw new BusinessException("Filme já existe");
        }
    }
}
