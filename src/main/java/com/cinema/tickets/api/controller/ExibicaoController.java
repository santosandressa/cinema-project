package com.cinema.tickets.api.controller;

import com.cinema.tickets.api.dto.ExibicaoDTO;

import com.cinema.tickets.api.mapper.ExibicaoMapper;
import com.cinema.tickets.domain.collection.Exibicao;
import com.cinema.tickets.domain.service.ExibicaoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api/v1/exibicao")
public class ExibicaoController {

    private final ExibicaoService exibicaoService;

    private final ExibicaoMapper exibicaoMapper;

    final Logger log = Logger.getLogger(ExibicaoController.class.getName());

    public ExibicaoController(ExibicaoService exibicaoService, ExibicaoMapper exibicaoMapper) {
        this.exibicaoService = exibicaoService;
        this.exibicaoMapper = exibicaoMapper;

    }

    @PostMapping
    public ResponseEntity<ExibicaoDTO> salvar(@RequestBody ExibicaoDTO exibicaoDTO){
        log.info("Requisição de Post - Exibição");

        Exibicao entity = exibicaoMapper.toEntity(exibicaoDTO);

        entity = this.exibicaoService.save(entity);

        ExibicaoDTO dto = exibicaoMapper.toDTO(entity);

        log.info("Requisição de Exibicao salva com sucesso");

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<ExibicaoDTO>> findAll(){
        log.info("Listando todas as Exibicoes");

        List<Exibicao> exibicoes = this.exibicaoService.getAll();

        List<ExibicaoDTO> dtos = exibicoes.stream().map(exibicaoMapper::toDTO).collect(Collectors.toList());


        if (exibicoes.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            for (ExibicaoDTO dto : dtos) {
                dto.add(linkTo(methodOn(ExibicaoController.class).findById(dto.getId())).withSelfRel());
            }
        }

        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<ExibicaoDTO> findById(@PathVariable String id){
        log.info("Listando Exibicao por id");

        Optional<Exibicao> exibicao = this.exibicaoService.getById(id);

        Optional<ExibicaoDTO> dto = exibicao.map(exibicaoMapper::toDTO);

        if (exibicao.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            dto.get().add(linkTo(methodOn(ExibicaoController.class).findAll()).withRel("Lista de exibições"));
        }

        return new ResponseEntity<>(dto.get(), HttpStatus.OK);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id){
        log.info("Deletando Exibicao por id");

        Optional<Exibicao> exibicao = this.exibicaoService.getById(id);

        if (exibicao.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.exibicaoService.delete(id);

        log.info("Requisição de Exibicao deletada com sucesso");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ExibicaoDTO> update(@PathVariable String id, @RequestBody ExibicaoDTO exibicaoDTO){
        log.info("Atualizando Exibicao por id");

        Optional<Exibicao> exibicao = this.exibicaoService.getById(id);

        if (exibicao.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Exibicao entity = exibicaoMapper.toEntity(exibicaoDTO);

        entity.setId(id);

        entity = this.exibicaoService.update(entity);

        ExibicaoDTO dto = exibicaoMapper.toDTO(entity);

        log.info("Requisição de Exibicao atualizada com sucesso");
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
