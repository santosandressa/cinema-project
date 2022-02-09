package com.cinema.tickets.api.controller;

import com.cinema.tickets.api.dto.EmailDTO;
import com.cinema.tickets.domain.collection.Email;
import com.cinema.tickets.domain.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(EmailController.class)
@EnableWebMvc
@ImportAutoConfiguration( FeignAutoConfiguration.class)
public class EmailControllerTest {

    static final String EMAIL_URL = "/api/v1/send-email";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private EmailService emailService;

    @Autowired
    Jackson2ObjectMapperBuilder mapperBuilder;

    private EmailDTO createEmailDTO() {
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setSendDateEmail(LocalDateTime.now());
        emailDTO.setEmailFrom("cinematickets@cinema.com");
        emailDTO.setEmailTo("andressasantosp0@gmail.com");
        emailDTO.setSubject("Test");
        emailDTO.setText("Test");

        return emailDTO;
    }

    private Email createEmail(){
        Email email = new Email();
        email.setSendDateEmail(LocalDateTime.now());
        email.setEmailFrom("cinematickets@cinema.com");
        email.setEmailTo("andressasantosp0@gmail.com");
        email.setSubject("Test");
        email.setText("Test");

        return email;
    }

    @Test
    @DisplayName("Deve enviar um email")
    public void shouldSendEmail() throws Exception {
        EmailDTO emailDTO = createEmailDTO();

        Email email = createEmail();

        BDDMockito.given(this.emailService.sendEmail(any(Email.class))).willReturn(email);

        ObjectMapper objectMapper = mapperBuilder.build();

        String json = objectMapper.writeValueAsString(emailDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(EMAIL_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isCreated());



    }


}
