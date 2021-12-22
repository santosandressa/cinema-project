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
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/cliente")
@CrossOrigin(origins = "*")
@Api(value = "Cliente Controller")
public class ClienteController {

    final Logger logger = Logger.getLogger(ClienteController.class.getName());

    @Autowired
    private ClienteService clienteService;

    @ApiOperation(value = "Cria um novo cliente")
    @PostMapping
    public ResponseEntity<Cliente> create(@Valid @RequestBody Cliente cliente) {
        logger.info("Criando um novo cliente");

        cliente = clienteService.save(cliente);
        return new ResponseEntity<>(cliente, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Busca um cliente pelo id")
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable String id) {
        logger.info("Buscando um cliente pelo id");
        Optional<Cliente> cliente = clienteService.findById(id);

        return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ApiOperation(value = "Deleta um cliente pelo id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        logger.info("Deletando um cliente pelo id");
        Optional<Cliente> cliente = clienteService.findById(id);

        if (!cliente.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            clienteService.delete(cliente.get());
            return ResponseEntity.noContent().build();
        }
    }

    @ApiOperation(value = "Atualiza um cliente pelo id")
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable String id, @Valid @RequestBody Cliente cliente) {
        logger.info("Atualizando um cliente pelo id");
        Optional<Cliente> clienteExistente = clienteService.findById(id);

        if (!clienteExistente.isPresent()) {
            return ResponseEntity.badRequest().build();
        } else {
            cliente.setId(id);
            cliente = clienteService.update(cliente);
            logger.info("Usuario atualizado com sucesso, retornando no corpo da requisicao o Usuario e Status OK");
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        }
    }
}