package com.cinema.tickets.api.controller;

import com.cinema.tickets.api.dto.SalaDTO;
import com.cinema.tickets.api.mapper.SalaMapper;
import com.cinema.tickets.domain.collection.Sala;
import com.cinema.tickets.domain.service.SalaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/sala")
@Tag(name = "Sala", description = "Sala")
public class SalaController {

    private final SalaService salaService;

    final Logger log = Logger.getLogger(SalaController.class.getName());

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @Autowired
    SalaMapper salaMapper;

    @Operation(summary = "Criar sala", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Sala criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao criar sala")
    @PostMapping("/cadastrar")
    public ResponseEntity<SalaDTO> salvar(@RequestBody SalaDTO salaDTO) {
        log.info("Requisição Post para salvar sala " + salaDTO.getNumSala());

        Sala entity = getSala(salaDTO);

        entity= this.salaService.save(entity);

        SalaDTO dto = getSalaDTO(entity);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @Operation(summary = "Buscar sala por id")
    @ApiResponse(responseCode = "200", description = "Sala encontrada")
    @ApiResponse(responseCode = "404", description = "Sala não encontrada")
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

    @Operation(summary="Listar salas")
    @ApiResponse(responseCode = "200", description = "Lista de salas")
    @ApiResponse(responseCode = "404", description = "Lista de salas vazia")
    @GetMapping
    public ResponseEntity<List<Sala>> buscarTodas(){
        log.info("Buscando todas as salas");
        List<Sala> salas = this.salaService.findAll();
        return new ResponseEntity<>(salas, HttpStatus.OK);
    }

    @Operation(summary = "Atualizar sala", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Sala atualizada")
    @ApiResponse(responseCode = "400", description = "Sala com dados inválidos")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<SalaDTO> atualizar(@PathVariable String id, @Valid @RequestBody SalaDTO salaDTO) {
        log.info("Atualizando sala " + salaDTO.getNumSala());
        Optional<Sala> salaExistente = this.salaService.findById(id);

        if(salaExistente.isPresent()){
            Sala entity = getSala(salaDTO);

            entity.setId(id);

            entity= this.salaService.save(entity);

            SalaDTO dto = getSalaDTO(entity);

            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            log.info("Sala não encontrada");

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    private SalaDTO getSalaDTO(Sala entity) {
        return salaMapper.salaToDTO(entity);
    }

    private Sala getSala(SalaDTO salaDTO) {
        return salaMapper.dtoToSala(salaDTO);
    }
}
