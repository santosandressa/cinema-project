package com.cinema.tickets.api.controller;

import com.cinema.tickets.api.dto.SalaDTO;
import com.cinema.tickets.api.mapper.SalaMapper;
import com.cinema.tickets.domain.collection.Sala;
import com.cinema.tickets.domain.service.SalaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/sala")
public class SalaController {

    private final SalaService salaService;

    final Logger log = Logger.getLogger(SalaController.class.getName());

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @Autowired
    SalaMapper salaMapper;

    @PostMapping
    public ResponseEntity<SalaDTO> salvar(@RequestBody SalaDTO salaDTO) {
        log.info("Requisição Post para salvar sala " + salaDTO.getNumSala());

        Sala entity = getSala(salaDTO);
        entity= this.salaService.save(entity);

        SalaDTO dto = getSalaDTO(entity);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<SalaDTO> buscar(@PathVariable String id) {
        log.info("Buscando sala " + id);
        Optional<Sala> entity = this.salaService.findById(id);

        if (entity.isPresent()) {
            SalaDTO dto = getSalaDTO(entity.get());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<List<Sala>> buscarTodas(){
        log.info("Buscando todas as salas");
        List<Sala> salas = this.salaService.findAll();
        return new ResponseEntity<>(salas, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SalaDTO> atualizar(@PathVariable String id, @RequestBody SalaDTO salaDTO) {
        log.info("Atualizando sala " + salaDTO.getNumSala());

        Sala entity = getSala(salaDTO);
        entity= this.salaService.save(entity);
        SalaDTO dto = getSalaDTO(entity);

        return new ResponseEntity<>(dto, HttpStatus.OK);

    }

    private SalaDTO getSalaDTO(Sala entity) {
        return salaMapper.salaToDTO(entity);
    }

    private Sala getSala(SalaDTO salaDTO) {
        return salaMapper.dtoToSala(salaDTO);
    }
}
