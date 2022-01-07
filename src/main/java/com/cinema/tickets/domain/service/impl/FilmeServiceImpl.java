package com.cinema.tickets.domain.service.impl;

import com.cinema.tickets.domain.collection.Filme;
import com.cinema.tickets.domain.exception.NotFoundException;
import com.cinema.tickets.domain.repository.FilmeRepository;
import com.cinema.tickets.domain.service.FilmeService;
import com.cinema.tickets.domain.strategy.FilmeStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class FilmeServiceImpl implements FilmeService {

    Logger log= Logger.getLogger(FilmeServiceImpl.class.getName());

    @Autowired
    FilmeStrategy filmeStrategy;

    @Autowired
    private final FilmeRepository filmeRepository;

    public FilmeServiceImpl(FilmeRepository filmeRepository) {
        this.filmeRepository = filmeRepository;
    }

    @Override
    public Filme save(Filme filme) {

        log.info("Salvando filme");

        if(filmeStrategy != null) {
            log.info("Filme validado");
            filmeStrategy.validateFilme(filme);
        }


        log.info("Filme: " + filme.getTitulo() + " salvo com sucesso");
        return this.filmeRepository.save(filme);
    }

    @Override
    public List<Filme> findAll() {
        log.info("Listando todos os filmes");
        return this.filmeRepository.findAll();
    }

    @Override
    public Optional<Filme> findById(String id) {
        log.info("Buscando filme pelo id: " + id);
        filmeStrategy.validateId(id);
        return this.filmeRepository.findById(id);
    }

    @Override
    public Filme update(Filme filme) {
        log.info("Atualizando filme");
        Optional<Filme> filmeOptional = this.filmeRepository.findById(filme.getId());

        if(filmeOptional.isEmpty()) {
            throw new NotFoundException("Filme não encontrado");
        }

        log.info("Filme: " + filme.getTitulo() + " atualizado com sucesso");
        return this.filmeRepository.save(filme);
    }

    @Override
    public void deleteById(String id) {
        log.info("Deletando filme pelo id: " + id);
        this.filmeRepository.deleteById(id);
        log.info("Filme deletado com sucesso");
    }
}
