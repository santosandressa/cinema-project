package com.cinema.tickets.api.controller;

import com.cinema.tickets.api.dto.FilmeDTO;
import com.cinema.tickets.api.mapper.FilmeMapper;
import com.cinema.tickets.domain.collection.Filme;
import com.cinema.tickets.domain.service.FilmeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@RequestMapping("/api/v1/filmes")
@CrossOrigin(origins = "*")
@Tag(name = "Filme", description = "Filme Controller")
public class FilmeController {

    final Logger logger = Logger.getLogger(ClienteController.class.getName());

    @Autowired
    FilmeService filmeService;

    @Autowired
    private FilmeMapper filmeMapper;

    @Operation(summary = "Cadastrar filme")
    @ApiResponse(responseCode = "201", description = "Filme cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro ao cadastrar filme")
    @PostMapping
    public ResponseEntity<FilmeDTO> cadastrarFilme(@Valid @RequestBody FilmeDTO filmeDTO) {
        logger.info("Cadastrando filme");
        Filme entity = filmeMapper.toEntity(filmeDTO);
        entity = filmeService.save(entity);
        FilmeDTO dto = filmeMapper.toDTO(entity);
       return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Operation(summary = "Buscar filme por id")
    @ApiResponse(responseCode = "200", description = "Filme encontrado")
    @ApiResponse(responseCode = "404", description = "Filme não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Filme> buscarFilmePorId(@PathVariable String id) {
        logger.info("Buscando filme por id");
        Optional<Filme> filme = filmeService.findById(id);
        return filme.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Listar todos os filmes")
    @ApiResponse(responseCode = "200", description = "Lista de filmes")
    @GetMapping
    public ResponseEntity<List<Filme>> buscarTodosFilmes() {
        logger.info("Buscando todos os filmes");
        List<Filme> filmes = filmeService.findAll();
        return new ResponseEntity<>(filmes, HttpStatus.OK);
    }

    @Operation(summary = "Atualizar filme")
    @ApiResponse(responseCode = "200", description = "Filme atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Filme não encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<Filme> atualizarFilme(@PathVariable String id, @Valid @RequestBody Filme filme) {
        logger.info("Atualizando filme");
        Optional<Filme> filmeExistente = filmeService.findById(id);

        if (filmeExistente.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        } else {
            filme.setId(id);
            filme = filmeService.save(filme);
            logger.info("Filme atualizado com sucesso");
            return new ResponseEntity<>(filme, HttpStatus.OK);
        }
    }

    @Operation(summary = "Deletar filme")
    @ApiResponse(responseCode = "204", description = "Filme deletado com sucesso")
    @ApiResponse(responseCode = "404", description = "Filme não encontrado")
    @DeleteMapping("/{id}")
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

