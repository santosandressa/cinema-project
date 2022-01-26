package com.cinema.tickets.security.handler;

import com.cinema.tickets.api.exception.Mensagem;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    final Mensagem mensagem = new Mensagem();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        mensagem.setStatus(response.getStatus());
        mensagem.setDataHora(OffsetDateTime.now());
        mensagem.setTitulo(exception.getMessage());

        Map<String, Object> data = new HashMap<>();
        data.put("status", mensagem.getStatus());
        data.put(
                "timestamp",
                mensagem.getDataHora());
        data.put(
                "titulo",
                mensagem.getTitulo());

        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());

        response.getOutputStream()
                .println(objectMapper.writeValueAsString(data));
    }
}
