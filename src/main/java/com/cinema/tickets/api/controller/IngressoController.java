package com.cinema.tickets.api.controller;

import com.cinema.tickets.api.dto.IngressoDTO;
import com.cinema.tickets.api.mapper.IngressoMapper;
import com.cinema.tickets.domain.collection.Ingresso;
import com.cinema.tickets.domain.service.IngressoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/ingresso")
public class IngressoController {

    private final IngressoService ingressoService;

    private final IngressoMapper ingressoMapper;

    final Logger logger = Logger.getLogger(IngressoController.class.getName());

    public IngressoController(IngressoService ingressoService, IngressoMapper ingressoMapper) {
        this.ingressoService = ingressoService;
        this.ingressoMapper = ingressoMapper;
    }


    @PostMapping
    public ResponseEntity<IngressoDTO> save(@RequestBody IngressoDTO ingressoDTO) {
        logger.info("Requisição recebida para salvar ingresso");

        Ingresso ingresso = ingressoMapper.toEntity(ingressoDTO);

        ingresso = ingressoService.save(ingresso);

        ingressoDTO = ingressoMapper.toDto(ingresso);

        return new ResponseEntity<>(ingressoDTO, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Ingresso> findById(@PathVariable String id) {
        logger.info("Requisição recebida para buscar ingresso por id");

        Optional<Ingresso> ingresso = ingressoService.findById(id);


        return new ResponseEntity<>(ingresso.get(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Ingresso>> findAll() {
        logger.info("Requisição recebida para buscar todos os ingressos");

        List<Ingresso> ingressos = ingressoService.findAll();

        return new ResponseEntity<>(ingressos, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Ingresso> delete(@PathVariable String id) {
        logger.info("Requisição recebida para deletar ingresso por id");

        ingressoService.delete(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
