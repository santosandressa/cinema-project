package com.cinema.tickets.api.controller;

import com.cinema.tickets.common.config.domain.service.impl.SalaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/sala")
public class SalaController {

    private final SalaService salaService;

    Logger log = Logger.getLogger(SalaController.class.getName());

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }
}
