package com.cinema.tickets.domain.strategy.impl;

import com.cinema.tickets.domain.collection.Filme;
import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.exception.NotFoundException;
import com.cinema.tickets.domain.repository.FilmeRepository;
import com.cinema.tickets.domain.strategy.FilmeStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FilmeStrategyImpl implements FilmeStrategy {

    @Autowired
    FilmeRepository filmeRepository;

    @Override
    public void validateFilme(Filme filme) {
        Optional<Filme> filmeExistsByNome = filmeRepository.findByTitulo(filme.getTitulo());

        if (filmeExistsByNome.isPresent()){
            throw new BusinessException("Filme já existe");
        }
    }

    @Override
    public void validateId(String id) {
        Optional<Filme> filmeId = filmeRepository.findById(id);

        if (filmeId.isEmpty()){
            throw new NotFoundException("Filme não encontrado");
        }

    }


}
