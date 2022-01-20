package com.cinema.tickets.domain.service;

import com.cinema.tickets.domain.collection.Cliente;
import com.cinema.tickets.domain.collection.Email;
import com.cinema.tickets.domain.exception.NotFoundException;
import com.cinema.tickets.domain.repository.ClienteRepository;
import com.cinema.tickets.domain.repository.EmailRepository;
import com.cinema.tickets.domain.service.impl.EmailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class EmailServiceTest {

    EmailService emailService;

    @MockBean
    EmailRepository emailRepository;

    @MockBean
    ClienteRepository clienteRepository;

    @MockBean
    JavaMailSender emailSender;


    @BeforeEach
    public void setUp() {
        emailService = new EmailServiceImpl(emailRepository,  clienteRepository, emailSender);
    }

    private Email createEmail(){
        Email email = new Email();
        email.setId("1");
        email.setSendDateEmail(LocalDateTime.now());
        email.setEmailFrom("cinematickets@cinema.com");
        email.setEmailTo("andressasantosp0@gmail.com");
        email.setSubject("Test");
        email.setText("Test");

        return email;
    }

    @Test
    @DisplayName("Deve salvar um email")
    public void shouldSaveEmail() {
        Email email = createEmail();
        Cliente cliente = new Cliente();

        email.setSendDateEmail(LocalDateTime.now());

        when(clienteRepository.findByEmail(anyString())).thenReturn(Optional.of(cliente));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(email.getEmailFrom());
        message.setTo(email.getEmailTo());
        message.setSubject(email.getSubject());
        message.setText(email.getText());

        emailSender.send(message);

        when(emailRepository.save(email)).thenReturn(email);

        Email emailSalvo = emailService.sendEmail(email);

        assertThat(emailSalvo.getId()).isNotNull();
        assertThat(emailSalvo.getSendDateEmail()).isNotNull();
        assertThat(emailSalvo.getEmailFrom()).isEqualTo(email.getEmailFrom());
        assertThat(emailSalvo.getEmailTo()).isEqualTo(email.getEmailTo());
        assertThat(emailSalvo.getSubject()).isEqualTo(email.getSubject());
        assertThat(emailSalvo.getText()).isEqualTo(email.getText());
    }

    @Test
    @DisplayName("Deve lançar exceção ao salvar um email com cliente não cadastrado")
    public  void shouldThrowExceptionWhenSaveEmailWithClienteNotExist() {

        when(clienteRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> emailService.sendEmail(createEmail()));
    }

}
