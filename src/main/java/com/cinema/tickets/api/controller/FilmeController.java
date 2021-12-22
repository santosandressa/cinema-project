package com.cinema.tickets.api.controller;

import com.cinema.tickets.domain.collection.Filme;
import com.cinema.tickets.domain.service.FilmeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Controller
@RequestMapping("/api/v1/filmes")
@CrossOrigin(origins = "*")
@Api(value = "Filme Controller")
public class FilmeController {

    final Logger logger = Logger.getLogger(ClienteController.class.getName());

    @Autowired
    private FilmeService filmeService;

    @ApiOperation(value = "Cadastra um novo filme")
    @PostMapping
    public ResponseEntity<Filme> cadastrarFilme(@Valid @RequestBody Filme filme) {
        logger.info("Cadastrando Filme");

        filme = filmeService.save(filme);
        return new ResponseEntity<>(filme, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Busca todos os filmes")
    @GetMapping
    public ResponseEntity<List<Filme>> buscarTodosFilmes() {
        logger.info("Buscando todos os filmes");

        List<Filme> filmes = filmeService.findAll();
        return new ResponseEntity<>(filmes, HttpStatus.OK);
    }

    @ApiOperation(value = "Busca um filme por id")
    @GetMapping("/{id}")
    public ResponseEntity<Filme> buscarFilmePorId(@PathVariable String id) {
        logger.info("Buscando filme por id");

        Optional<Filme> filme = filmeService.findById(id);

        return filme.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
