package com.cinema.tickets.api.controller;

import com.cinema.tickets.common.config.domain.service.impl.SalaService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(SalaController.class)
@AutoConfigureMockMvc
@EnableWebMvc
public class SalaControllerTest {

    static final String SALA_API = "/api/v1/sala";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SalaService salaService;



}

