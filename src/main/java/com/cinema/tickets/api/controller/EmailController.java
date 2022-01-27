package com.cinema.tickets.api.controller;

import com.cinema.tickets.api.dto.EmailDTO;
import com.cinema.tickets.api.mapper.EmailMapper;
import com.cinema.tickets.domain.collection.Email;
import com.cinema.tickets.domain.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;


@RestController
@RequestMapping("/api/v1/send-email")
@Tag(name = "Email", description = "Envio de emails")
public class EmailController {

    private final EmailService emailService;

    private final EmailMapper emailMapper;

    final Logger log = Logger.getLogger(EmailController.class.getName());

    public EmailController(EmailService emailService, EmailMapper emailMapper) {
        this.emailService = emailService;
        this.emailMapper = emailMapper;
    }

    @Operation(summary = "Envia um email")
    @ApiResponse(responseCode = "201", description = "Email enviado com sucesso")
    @PostMapping
    public ResponseEntity<EmailDTO> sendEmail(@RequestBody EmailDTO emailDTO) {
        log.info("Enviando email");
        Email entity = emailMapper.toEntity(emailDTO);

        entity = emailService.sendEmail(entity);

        EmailDTO dto = emailMapper.toDto(entity);

        log.info("Email enviado");
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
}
