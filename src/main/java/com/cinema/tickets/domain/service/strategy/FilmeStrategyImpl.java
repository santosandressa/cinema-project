package com.cinema.tickets.domain.service.strategy;

import com.cinema.tickets.domain.collection.Filme;
import com.cinema.tickets.domain.exception.BusinessException;
import org.springframework.stereotype.Component;

@Component
public class FilmeStrategyImpl implements FilmeStrategy {


    @Override
    public void validateFilme(Filme filme) {
        boolean filmeExiste = filme.getId() != null;

        if(filmeExiste) {
            throw new BusinessException("Filme já existe");
        }


        boolean filmeExistsByNome = filme.getTitulo() != null;

        if(filmeExistsByNome) {
            throw new BusinessException("Filme já existe");
        }

    }
}
