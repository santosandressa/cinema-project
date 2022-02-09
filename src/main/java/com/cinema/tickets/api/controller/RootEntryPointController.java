package com.cinema.tickets.api.controller;

import com.cinema.tickets.api.dto.RootEntryPointDTO;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
public class RootEntryPointController {

    final Logger log = Logger.getLogger(RootEntryPointController.class.getName());

    @GetMapping
    public RootEntryPointDTO getRootEntryPoint() {
        var model = new RootEntryPointDTO();

        log.info("Adicionando links no RootEntryPoint");

        model.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(ExibicaoController.class).findAll()
        ).withRel("exibicoes"));


        model.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(SalaController.class).buscarTodas()
        ).withRel("salas"));


        model.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(FilmeController.class).buscarTodosFilmes()
        ).withRel("filmes"));


        return model;
    }
}
