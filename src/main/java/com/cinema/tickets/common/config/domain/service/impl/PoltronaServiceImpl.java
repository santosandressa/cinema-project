package com.cinema.tickets.common.config.domain.service.impl;

import com.cinema.tickets.common.config.domain.collection.Poltrona;
import com.cinema.tickets.common.config.domain.exception.BusinessException;
import com.cinema.tickets.common.config.domain.repository.PoltronaRepository;
import com.cinema.tickets.common.config.domain.service.PoltronaService;
import org.springframework.stereotype.Service;

@Service
public class PoltronaServiceImpl implements PoltronaService {

    private final PoltronaRepository poltronaRepository;

    public PoltronaServiceImpl(PoltronaRepository poltronaRepository) {
        this.poltronaRepository = poltronaRepository;
    }

    @Override
    public Poltrona save(Poltrona poltrona) {

        if(poltrona.getCadeira() == null && poltrona.getFileira() == null){
            throw new BusinessException("cadeira não pode ser nula");
        }
        return this.poltronaRepository.save(poltrona);
    }
}
