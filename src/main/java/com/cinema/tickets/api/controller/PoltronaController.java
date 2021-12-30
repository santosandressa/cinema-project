package com.cinema.tickets.api.controller;

import com.cinema.tickets.api.dto.PoltronaDTO;
import com.cinema.tickets.api.mapper.PoltronaMapper;
import com.cinema.tickets.domain.collection.Poltrona;
import com.cinema.tickets.domain.service.PoltronaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/poltronas")
public class PoltronaController {

    private final PoltronaService poltronaService;

    @Autowired
    PoltronaMapper poltronaMapper;

    public PoltronaController(PoltronaService poltronaService) {
        this.poltronaService = poltronaService;
    }

    @PostMapping
    public ResponseEntity<PoltronaDTO> salvar(@Valid @RequestBody PoltronaDTO poltronaDTO) {

        Poltrona entity = poltronaMapper.toEntity(poltronaDTO);
        entity = this.poltronaService.save(entity);

        PoltronaDTO dto = poltronaMapper.toDTO(entity);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
