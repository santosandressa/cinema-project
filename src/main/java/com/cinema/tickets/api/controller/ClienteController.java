package com.cinema.tickets.api.controller;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cinema.tickets.api.dto.ClienteDTO;
import com.cinema.tickets.api.mapper.ClienteMapper;
import com.cinema.tickets.domain.collection.Cliente;
import com.cinema.tickets.domain.collection.Role;
import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;


import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/v1/cliente")
@CrossOrigin(origins = "*")
@Tag(name = "Cliente", description = "Cliente Controller")
public class ClienteController {

    final Logger log = Logger.getLogger(ClienteController.class.getName());

    private final ClienteService clienteService;

    private final ClienteMapper clienteMapper;

    public ClienteController(ClienteService clienteService, ClienteMapper clienteMapper) {
        this.clienteService = clienteService;
        this.clienteMapper = clienteMapper;
    }

    @Operation(summary = "Cadastrar um cliente")
    @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso")
    @ApiResponse(responseCode = "400", description = "Cliente com dados inválidos")
    @PostMapping("/cadastrar")
    public ResponseEntity<ClienteDTO> create(@RequestBody @Valid ClienteDTO clienteDTO) {
        log.info("Criando um novo cliente");

        Cliente entity = clienteMapper.toEntity(clienteDTO);

        entity = this.clienteService.save(entity);

        clienteService.addRole(entity.getEmail(), "ROLE_USER");

        ClienteDTO dto = clienteMapper.toDTO(entity);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @Operation(summary = "Buscar um cliente pelo id")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso", content = @Content(schema = @Schema(implementation = Cliente.class)))
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> findById(@PathVariable String id) {
        log.info("Buscando um cliente pelo id");

        Optional<Cliente> cliente = clienteService.findById(id);

        return cliente.map(entity -> new ResponseEntity<>(clienteMapper.toDTO(entity), HttpStatus.OK)).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar todos os clientes")
    @ApiResponse(responseCode = "200", description = "Lista de clientes encontrada com sucesso")
    @GetMapping("/listar")
    public ResponseEntity<List<Cliente>> findAllClientes() {
        log.info("Buscando todos os clientes");

        List<Cliente> clientes = clienteService.findAll();

        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @Operation(summary = "Deletar um cliente pelo id")
    @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        log.info("Deletando um cliente pelo id");

        Optional<Cliente> cliente = clienteService.findById(id);

        if (cliente.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {

            clienteService.delete(cliente.get());

            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Atualizar um cliente pelo id")
    @ApiResponse(responseCode = "400", description = "Cliente não encontrado, ou dados inválidos")
    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable String id, @Valid @RequestBody ClienteDTO clienteDTO) {
        log.info("Atualizando um cliente pelo id" + id);

        Optional<Cliente> clienteExistente = clienteService.findById(id);

        if (clienteExistente.isEmpty()) {

            log.info("Cliente não encontrado");

            return ResponseEntity.notFound().build();

        } else {
            Cliente entity = clienteMapper.toEntity(clienteDTO);

            entity.setId(id);

            entity = clienteService.update(entity);

            ClienteDTO dto = clienteMapper.toDTO(entity);

            log.info("Usuario atualizado com sucesso");
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }
    }


    @PostMapping("/role/salvar")
    public ResponseEntity<Role> salvarRole(@RequestBody Role role) {
        log.info("Criando um novo role");

        Role entity = this.clienteService.saveRole(role);

        return new ResponseEntity<>(entity, HttpStatus.CREATED);
    }

    @PostMapping("/role/addCliente")
    public ResponseEntity<?> addClienteRole(@RequestBody String email, String nomeRole) {
        log.info("Adicionando um cliente a um role");

        Optional<Cliente> cliente = clienteService.findClienteByEmail(email);

        Role role = clienteService.findRoleByNome(nomeRole);

        clienteService.addRole(cliente.get().getEmail(), role.getNome());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authHeader = request.getHeader(AUTHORIZATION);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            try {
                String refreshToken = authHeader.substring("Bearer ".length());

                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes(StandardCharsets.UTF_8));

                JWTVerifier verifier = JWT.require(algorithm).build();

                DecodedJWT decodedJWT = verifier.verify(refreshToken);

                String email = decodedJWT.getSubject();

                Optional<Cliente> cliente = clienteService.findClienteByEmail(email);

                String accessToken = JWT.create()
                        .withSubject(cliente.get().getEmail())
                        .withExpiresAt(new java.util.Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withIssuer("auth0")
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", cliente.get().getRoles().stream().map(Role::getNome).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

                log.info("Token gerado: " + accessToken);

            } catch (Exception e) {
                log.info("Error: " + e.getMessage());
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());

                Map<String, String> error = new HashMap<>();
                error.put("error", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);

                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new BusinessException("Refresh token is missing");
        }

    }

    @PostMapping("/login")
    @ApiResponse(responseCode = "200", description = "Login realizado com sucesso")
    @Operation(summary = "Login", description = "Realiza o login do usuário")
    public ResponseEntity<Cliente> login(@RequestBody String email, String senha){
        log.info("Fazendo login");

        Cliente cliente = clienteService.login(email, senha);

        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

}