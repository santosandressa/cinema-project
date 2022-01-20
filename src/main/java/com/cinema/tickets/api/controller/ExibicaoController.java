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
    public ResponseEntity<ExibicaoDTO> salvar(@RequestBody ExibicaoDTO exibicaoDTO) {
        log.info("Salvando Exibicao");

        Exibicao entity = exibicaoMapper.toEntity(exibicaoDTO);

        entity = this.exibicaoService.save(entity);

        ExibicaoDTO dto = exibicaoMapper.toDTO(entity);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Exibicao>> findAll(){
        log.info("Listando todas as Exibicoes");

        List<Exibicao> exibicoes = this.exibicaoService.getAll();

        return new ResponseEntity<>(exibicoes, HttpStatus.OK);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Exibicao> findById(@PathVariable String id){
        log.info("Listando Exibicao por id");

        Optional<Exibicao> exibicao = this.exibicaoService.getById(id);

        if (exibicao.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(exibicao.get(), HttpStatus.OK);
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
