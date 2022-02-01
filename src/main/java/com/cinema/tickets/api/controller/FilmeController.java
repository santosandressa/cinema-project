package com.cinema.tickets.api.controller;

import com.cinema.tickets.api.dto.FilmeDTO;
import com.cinema.tickets.api.mapper.FilmeMapper;
import com.cinema.tickets.domain.collection.Filme;
import com.cinema.tickets.domain.service.FilmeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/filmes")
@CrossOrigin(origins = "*")
@Tag(name = "Filme", description = "Filme Controller")
public class FilmeController {

    final Logger logger = Logger.getLogger(ClienteController.class.getName());

    private final FilmeService filmeService;

    private final FilmeMapper filmeMapper;

    public FilmeController(FilmeService filmeService, FilmeMapper filmeMapper) {
        this.filmeService = filmeService;
        this.filmeMapper = filmeMapper;
    }

    @Operation(summary = "Cadastrar filme", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Filme cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao cadastrar filme")
    @PostMapping("/cadastrar")
    public ResponseEntity<FilmeDTO> cadastrarFilme(@Valid @RequestBody FilmeDTO filmeDTO) {

        logger.info("Cadastrando filme");

        Filme entity = filmeMapper.toEntity(filmeDTO);

        entity = filmeService.save(entity);

        FilmeDTO dto = filmeMapper.toDTO(entity);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar todos os filmes")
    @ApiResponse(responseCode = "200", description = "Lista de filmes")
    @GetMapping
    public ResponseEntity<List<FilmeDTO>> buscarTodosFilmes() {
        logger.info("Buscando todos os filmes");

        List<Filme> filmes = this.filmeService.findAll();

        List<FilmeDTO> dtos = filmes.stream().map(filmeMapper::toDTO).collect(Collectors.toList());

        if (filmes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (FilmeDTO dto : dtos) {
                dto.add(linkTo(methodOn(FilmeController.class).buscarFilmePorId(dto.getId())).withSelfRel());
            }
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }


    @Operation(summary = "Buscar filme por id")
    @ApiResponse(responseCode = "200", description = "Filme encontrado")
    @ApiResponse(responseCode = "404", description = "Filme não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<FilmeDTO> buscarFilmePorId(@PathVariable String id) {

        logger.info("Buscando filme por id");

        Optional<Filme> filme = filmeService.findById(id);

        Optional<FilmeDTO> dto = filme.map(filmeMapper::toDTO);

        if(dto.isPresent()) {
            dto.get().add(linkTo(methodOn(FilmeController.class).buscarFilmePorId(id)).withRel("filme"));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(dto.orElse(null), HttpStatus.OK);
    }


    @Operation(summary = "Atualizar filme", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Filme atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Filme não encontrado, ou dados inválidos")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<FilmeDTO> atualizarFilme(@PathVariable String id, @Valid @RequestBody FilmeDTO filmeDTO) {
        logger.info("Atualizando filme");

        Filme entity = filmeMapper.toEntity(filmeDTO);

        entity.setId(id);

        entity = filmeService.update(entity);

        FilmeDTO dto = filmeMapper.toDTO(entity);

        logger.info("Filme atualizado com sucesso");

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }


    @Operation(summary = "Deletar filme", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Filme deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Filme não encontrado")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarFilme(@PathVariable String id) {
        logger.info("Deletando filme");
        Optional<Filme> filmeExistente = filmeService.findById(id);

        if (filmeExistente.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            filmeService.deleteById(id);
            logger.info("Filme deletado com sucesso");
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}

