package com.cinema.tickets.domain.service.impl;


import com.cinema.tickets.domain.collection.Sala;
import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.exception.NotFoundException;

import com.cinema.tickets.domain.repository.SalaRepository;
import com.cinema.tickets.domain.service.SalaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class SalaServiceImpl implements SalaService {

    final Logger log = Logger.getLogger(SalaServiceImpl.class.getName());

    private  final SalaRepository salaRepository;

    public SalaServiceImpl(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    @Override
    public Sala save(Sala sala) {
        log.info("Salvando Sala: " + sala.getNumSala());
        Boolean salaNumero = salaRepository.existsByNumSala(sala.getNumSala());

        if (salaNumero) {
            throw new BusinessException("Sala já cadastrada");
        }

        return salaRepository.save(sala);
    }

    @Override
    public List<Sala> findAll() {
        log.info("Buscando todas as Salas");
        return salaRepository.findAll();
    }

    @Override
    public Optional<Sala> findById(String id) {
        log.info("Buscando Sala por id: " + id);

        Optional<Sala> salaId = salaRepository.findById(id);

        if (salaId.isEmpty()){
            throw new NotFoundException("Sala não encontrada");
        }
        return salaId;
    }

    @Override
    public Sala update(Sala sala) {
        log.info("Atualizando Sala: " + sala.getNumSala());

        Optional<Sala> salaId = salaRepository.findById(sala.getId());

        if (salaId.isEmpty() || sala.getId() == null){
            log.info("Sala não encontrada");
            throw new BusinessException("Sala não encontrada, ou dados inválidos");
        } else {

            sala.setId(salaId.get().getId());
            return salaRepository.save(sala);
        }
    }
}
