package com.cinema.tickets.api.controller;


import com.cinema.tickets.api.dto.ClienteDTO;
import com.cinema.tickets.api.mapper.ClienteMapper;
import com.cinema.tickets.domain.collection.Cliente;
import com.cinema.tickets.domain.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/api/v1/cliente")
@CrossOrigin(origins = "*")
@Tag(name = "Cliente" , description = "Cliente Controller")
public class ClienteController {

    final Logger logger = Logger.getLogger(ClienteController.class.getName());

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteMapper clienteMapper;

    @Operation(summary = "Cadastrar um cliente", description = "Cadastrar um cliente")
    @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso")
    @PostMapping
    public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO clienteDTO) {
        logger.info("Criando um novo cliente");

        Cliente entity = clienteMapper.toEntity(clienteDTO);
        entity = clienteService.save(entity);

        ClienteDTO dto = clienteMapper.toDTO(entity);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @Operation(summary = "Buscar um cliente pelo id", description = "Buscar um cliente pelo id")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso", content = @Content(schema =  @Schema(implementation = Cliente.class)))
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable String id) {
        logger.info("Buscando um cliente pelo id");

        Optional<Cliente> cliente = clienteService.findById(id);

        return cliente.map(entity -> new ResponseEntity<>(clienteMapper.toDTO(entity), HttpStatus.OK)).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> findAllClientes() {
        logger.info("Buscando todos os clientes");
        List<Cliente> clientes = clienteService.findAll();

        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        logger.info("Deletando um cliente pelo id");
        Optional<Cliente> cliente = clienteService.findById(id);

        if (cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            clienteService.delete(cliente.get());
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> update(@PathVariable String id, @Valid @RequestBody Cliente cliente) {
        logger.info("Atualizando um cliente pelo id");
        Optional<Cliente> clienteExistente = clienteService.findById(id);

        if (clienteExistente.isEmpty()) {
            return ResponseEntity.badRequest().build();
        } else {
            cliente.setId(id);
            cliente = clienteService.update(cliente);
            logger.info("Usuario atualizado com sucesso, retornando no corpo da requisicao o Usuario e Status OK");
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        }
    }
}