package com.cinema.tickets.domain.service;


import com.cinema.tickets.domain.repository.ExibicaoRepository;
import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class ScheduleService  {

    private static final String CRON_EXPIRED_SCHEDULE = "0 0 0 ? 1/3 SUN#3";

    final Logger log = Logger.getLogger(ScheduleService.class.getName());

   private final ExibicaoRepository exibicaoRepository;


    public ScheduleService(ExibicaoRepository exibicaoRepository) {
        this.exibicaoRepository = exibicaoRepository;

    }

    @Scheduled(cron = CRON_EXPIRED_SCHEDULE)
    public void deleteExpiredExibition() {
        log.info("Deletando filmes fora de cartaz");

        exibicaoRepository.deleteById(exibicaoRepository.findAll().get(0).getId());

        log.info("Filmes fora de cartaz deletados");
    }
}

