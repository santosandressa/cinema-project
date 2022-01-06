package com.cinema.tickets.api.controller;

import com.cinema.tickets.api.dto.PoltronaDTO;
import com.cinema.tickets.api.mapper.PoltronaMapper;
import com.cinema.tickets.domain.collection.Poltrona;
import com.cinema.tickets.domain.exception.NotFoundException;
import com.cinema.tickets.domain.service.PoltronaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/poltronas")
@Tag(name = "Poltrona", description = "Poltrona")
public class PoltronaController {

    private final PoltronaService poltronaService;

    final Logger logger = Logger.getLogger(PoltronaController.class.getName());

    @Autowired
    PoltronaMapper poltronaMapper;

    public PoltronaController(PoltronaService poltronaService) {
        this.poltronaService = poltronaService;
    }

    @Operation(summary = "Cria uma poltrona")
    @ApiResponse(responseCode = "201", description = "Poltrona criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao criar poltrona")
    @PostMapping
    public ResponseEntity<PoltronaDTO> salvar(@Valid @RequestBody PoltronaDTO poltronaDTO) {

        logger.info("Criando poltrona");

        Poltrona entity = poltronaMapper.toEntity(poltronaDTO);
        entity = this.poltronaService.save(entity);

        PoltronaDTO dto = poltronaMapper.toDTO(entity);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<Poltrona>> listar() {
        logger.info("Listando poltronas");
        Iterable<Poltrona> poltronas = this.poltronaService.findAll();
        return new ResponseEntity<>(poltronas, HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PoltronaDTO> buscarPorId(@PathVariable String id) {
        logger.info("Buscando poltrona por id");
        Optional<Poltrona> poltrona = this.poltronaService.findById(id);
        return poltrona.map(entity -> new ResponseEntity<>(poltronaMapper.toDTO(entity), HttpStatus.OK)).orElseThrow(() -> new NotFoundException("Poltrona não encontrada"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PoltronaDTO> update(@PathVariable String id,@RequestBody PoltronaDTO poltronaDTO) {
        logger.info("Atualizando poltrona");
        Optional<Poltrona> poltrona = this.poltronaService.findById(id);

        if(poltrona.isEmpty()){
            return ResponseEntity.badRequest().build();
        } else {
            poltronaDTO.setId(id);

            Poltrona entity = poltronaMapper.toEntity(poltronaDTO);

            entity = this.poltronaService.save(entity);

            PoltronaDTO dto = poltronaMapper.toDTO(entity);

            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
    }


}
