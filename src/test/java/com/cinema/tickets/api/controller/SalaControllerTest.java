package com.cinema.tickets.api.controller;


import com.cinema.tickets.annotations.WithMockAdmin;
import com.cinema.tickets.api.dto.SalaDTO;
import com.cinema.tickets.domain.collection.Sala;
import com.cinema.tickets.domain.service.SalaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(SalaController.class)
@AutoConfigureMockMvc
public class SalaControllerTest {

    static final String SALA_URL = "/api/v1/sala";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SalaService salaService;

    private SalaDTO createSalaDTO() {
        SalaDTO salaDTO = new SalaDTO();
        salaDTO.setNumSala(1);
        salaDTO.setSala3D(true);
        salaDTO.setCapacidade(216);

        salaDTO.setPoltrona(salaDTO.getPoltrona());
        return salaDTO;
    }

    private Sala createSala(){
        Sala sala = new Sala();
        sala.setId("1");
        sala.setNumSala(1);
        sala.setSala3D(true);
        sala.setCapacidade(216);

        sala.setPoltrona(sala.getPoltrona());
        return sala;
    }

    @Test
    @DisplayName("Deve cadastrar uma sala")
    @WithMockAdmin
    public void shouldCreateSala() throws Exception{
        SalaDTO salaDTO = createSalaDTO();

        Sala sala = createSala();

        BDDMockito.given(salaService.save(any(Sala.class))).willReturn(sala);

        String json = new ObjectMapper().writeValueAsString(salaDTO);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(SALA_URL.concat("/cadastrar"))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);


        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value("1"))
                .andExpect(jsonPath("$.numSala").value(salaDTO.getNumSala()))
                .andExpect(jsonPath("$.sala3D").value(salaDTO.getSala3D()))
                .andExpect(jsonPath("$.capacidade").value(salaDTO.getCapacidade()))
                .andExpect(jsonPath("$.poltrona").value(sala.getPoltrona()));
    }


}
