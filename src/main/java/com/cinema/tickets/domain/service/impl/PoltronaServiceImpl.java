package com.cinema.tickets.domain.service.impl;

import com.cinema.tickets.domain.collection.Poltrona;
import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.repository.PoltronaRepository;
import com.cinema.tickets.domain.service.PoltronaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PoltronaServiceImpl implements PoltronaService {

    private final PoltronaRepository poltronaRepository;

    public PoltronaServiceImpl(PoltronaRepository poltronaRepository) {
        this.poltronaRepository = poltronaRepository;
    }

    @Override
    public Poltrona save(Poltrona poltrona) {

        Boolean cadeiraAndFileira = this.poltronaRepository.existsByFileiraAndCadeira(poltrona.getFileira(), poltrona.getCadeira());

        if(poltrona.getCadeira() == null && poltrona.getFileira() == null){
            throw new BusinessException("Cadeira não pode ser nula");
        }

        if(cadeiraAndFileira){
            throw new BusinessException("Cadeira e fileira já existem");
        }

        return this.poltronaRepository.save(poltrona);
    }

    @Override
    public Optional<Poltrona> findById(String id) {
        return this.poltronaRepository.findById(id);
    }

    @Override
    public List<Poltrona> findAll() {
        return this.poltronaRepository.findAll();
    }


    @Override
    public Poltrona update(Poltrona poltrona) {
        return this.poltronaRepository.save(poltrona);
    }
}
