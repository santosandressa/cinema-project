package com.cinema.tickets.domain.service.impl;

import com.cinema.tickets.domain.collection.Filme;
import com.cinema.tickets.domain.repository.FilmeRepository;
import com.cinema.tickets.domain.service.FilmeService;
import com.cinema.tickets.domain.strategy.FilmeStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilmeServiceImpl implements FilmeService {

    @Autowired
    private FilmeStrategy filmeStrategy;

    @Autowired
    private FilmeRepository filmeRepository;

    @Override
    public Filme save(Filme filme) {
        filmeStrategy.validateFilme(filme);
        return this.filmeRepository.save(filme);
    }

    @Override
    public List<Filme> findAll() {
        return this.filmeRepository.findAll();
    }

    @Override
    public Optional<Filme> findById(String id) {
        return this.filmeRepository.findById(id);
    }

    @Override
    public Filme update(Filme filme) {
        return this.filmeRepository.save(filme);
    }

    @Override
    public void deleteById(String id) {
        this.filmeRepository.deleteById(id);
    }
}
