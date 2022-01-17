package com.cinema.tickets.domain.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    private static final String CRON_EXPIRED_SCHEDULE = "0 0 0 ? 1/3 SUN#3";

    private final FilmeService filmeService;

    public ScheduleService(FilmeService filmeService) {
        this.filmeService = filmeService;
    }

    @Scheduled(cron = CRON_EXPIRED_SCHEDULE)
    public void deleteExpiredSchedules() {
        filmeService.deleteById(filmeService.findAll().get(0).getId());
    }
}
