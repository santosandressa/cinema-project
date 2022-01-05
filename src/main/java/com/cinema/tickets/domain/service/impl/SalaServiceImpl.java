package com.cinema.tickets.domain.service.impl;

import com.cinema.tickets.domain.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class SalaServiceImpl implements SalaService {

    private final SalaRepository salaRepository;

    final Logger log = Logger.getLogger(SalaServiceImpl.class.getName());

    public SalaServiceImpl(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }
}
