package com.cinema.tickets.domain.service.impl;

import com.cinema.tickets.domain.collection.Poltrona;
import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.exception.NotFoundException;
import com.cinema.tickets.domain.repository.PoltronaRepository;
import com.cinema.tickets.domain.service.PoltronaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class PoltronaServiceImpl implements PoltronaService {

    private final PoltronaRepository poltronaRepository;

    Logger log= Logger.getLogger(PoltronaServiceImpl.class.getName());

    public PoltronaServiceImpl(PoltronaRepository poltronaRepository) {
        this.poltronaRepository = poltronaRepository;
    }

    @Override
    public Poltrona save(Poltrona poltrona) {

        log.info("Salvando poltrona");
        Boolean cadeiraAndFileira = this.poltronaRepository.existsByFileiraAndCadeira(poltrona.getFileira(), poltrona.getCadeira());

        if(poltrona.getCadeira() == null && poltrona.getFileira() == null){
            throw new BusinessException("Cadeira não pode ser nula");
        }

        if(cadeiraAndFileira){
            throw new BusinessException("Cadeira e fileira já existem");
        }

        log.info("Poltrona da fileira: " + poltrona.getFileira() + " e cadeira: " + poltrona.getCadeira() + " salva");

        return this.poltronaRepository.save(poltrona);
    }

    @Override
    public Optional<Poltrona> findById(String id) {

        log.info("Buscando poltrona pelo id: " + id);

        return this.poltronaRepository.findById(id);
    }

    @Override
    public List<Poltrona> findAll() {

        log.info("Buscando todas as poltronas");

        return this.poltronaRepository.findAll();
    }


    @Override
    public Poltrona update(Poltrona poltrona) {

        log.info("Atualizando poltrona");

        Optional<Poltrona> poltronaOptional = this.poltronaRepository.findById(poltrona.getId());

        if(poltronaOptional.isEmpty() || poltronaOptional.get().getId() == null){
            throw new NotFoundException("Poltrona não encontrada");
        }

        log.info("Poltrona da fileira: " + poltrona.getFileira() + " e cadeira " + poltrona.getCadeira() + " atualizada");
        return this.poltronaRepository.save(poltrona);
    }
}
