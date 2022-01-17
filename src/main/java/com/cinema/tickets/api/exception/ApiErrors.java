package com.cinema.tickets.api.exception;

import com.cinema.tickets.domain.exception.BusinessException;
import com.cinema.tickets.domain.exception.NotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ApiErrors extends ResponseEntityExceptionHandler {

    final Mensagem mensagem = new Mensagem();

    private final  MessageSource messageSource;

    public ApiErrors(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<Mensagem.Campo> campos = new ArrayList<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String nome = ((FieldError) error).getField();
            String mensagem = messageSource.getMessage(error, LocaleContextHolder.getLocale());

            campos.add(new Mensagem.Campo(nome, mensagem));
        }

        mensagem.setStatus(status.value());
        mensagem.setDataHora(OffsetDateTime.now());
        mensagem.setTitulo("Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente");
        mensagem.setCampos(campos);
        return handleExceptionInternal(ex, mensagem, headers, status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleNegocio(BusinessException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        mensagem.setStatus(status.value());
        mensagem.setDataHora(OffsetDateTime.now());
        mensagem.setTitulo(ex.getMessage());
        return handleExceptionInternal(ex, mensagem, new HttpHeaders(), status, request);
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFound(NotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        mensagem.setStatus(status.value());
        mensagem.setDataHora(OffsetDateTime.now());
        mensagem.setTitulo(ex.getMessage());
        return handleExceptionInternal(ex, mensagem, new HttpHeaders(), status, request);
    }

}