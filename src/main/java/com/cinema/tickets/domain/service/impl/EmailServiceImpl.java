package com.cinema.tickets.domain.service.impl;

import com.cinema.tickets.domain.collection.Cliente;
import com.cinema.tickets.domain.collection.Email;
import com.cinema.tickets.domain.exception.NotFoundException;
import com.cinema.tickets.domain.repository.ClienteRepository;
import com.cinema.tickets.domain.repository.EmailRepository;
import com.cinema.tickets.domain.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;

    private final ClienteRepository clienteRepository;

    private final JavaMailSender emailSender;

    final Logger log = Logger.getLogger("EmailServiceImpl");

    public EmailServiceImpl(EmailRepository emailRepository, ClienteRepository clienteRepository, JavaMailSender emailSender) {
        this.emailRepository = emailRepository;

        this.clienteRepository = clienteRepository;
        this.emailSender = emailSender;
    }



    @Override
    public Email sendEmail(Email email) {

        email.setSendDateEmail(LocalDateTime.now());

        Optional<Cliente> cliente = clienteRepository.findByEmail(email.getEmailTo());

        if(cliente.isEmpty()){
            log.info("Cliente não encontrado");
            throw new NotFoundException("Cliente não encontrado");
        } else {
                log.info("Sending email" );
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(email.getEmailFrom());
                message.setTo(email.getEmailTo());
                message.setSubject(email.getSubject());
                message.setText(email.getText());
                emailSender.send(message);
                log.info("Email sent");
        } return emailRepository.save(email);

    }
}
