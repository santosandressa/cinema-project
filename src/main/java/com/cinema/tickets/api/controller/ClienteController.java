package com.cinema.tickets.api.controller;


import com.cinema.tickets.domain.collection.Cliente;
import com.cinema.tickets.domain.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/cliente")
@CrossOrigin(origins = "*")
@Api(value="Cliente Controller")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    Logger logger = Logger.getLogger(ClienteController.class.getName());

    @ApiOperation(value= "Cria um novo cliente")
    @PostMapping
    public ResponseEntity<Cliente> create(@Valid @RequestBody Cliente cliente) {
        logger.info("Criando um novo cliente");

        cliente = clienteService.save(cliente);
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }
}
