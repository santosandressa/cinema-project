package com.cinema.tickets.domain.strategy.impl;

import com.cinema.tickets.domain.collection.Filme;
import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.strategy.FilmeStrategy;
import org.springframework.stereotype.Component;

@Component
public class FilmeStrategyImpl implements FilmeStrategy {


    @Override
    public void validateFilme(Filme filme) {
        boolean filmeExiste = filme.getId() != null;

        if (filmeExiste) {
            throw new BusinessException("Filme já existe");
        }


        boolean filmeExistsByNome = filme.getTitulo() != null;

        if (filmeExistsByNome) {
            throw new BusinessException("Filme já existe");
        }

    }
}
