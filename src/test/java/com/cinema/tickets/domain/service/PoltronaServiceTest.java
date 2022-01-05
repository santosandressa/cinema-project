package com.cinema.tickets.domain.service;

import com.cinema.tickets.domain.collection.Poltrona;
import com.cinema.tickets.domain.repository.PoltronaRepository;
import com.cinema.tickets.domain.service.impl.PoltronaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PoltronaServiceTest {

    PoltronaService poltronaService;

    @MockBean
    PoltronaRepository poltronaRepository;

    @BeforeEach
    public void setUp() {
        poltronaService = new PoltronaServiceImpl(poltronaRepository);
    }

    private Poltrona createPoltrona() {
        Poltrona poltrona = new Poltrona();
        poltrona.setId("1");
        poltrona.setCadeira(new String[]{"A1","A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9", "A10", "A11", "A12", "A13", "A14", "B1", "B2", "B3", "B4", "B5", "B6", "B7", "B8", "B9", "B10", "B11", "B12", "B13", "B14", 
                "C1", "C2", "C3", "C4", "C5", "C6", "C7", "C8", "C9", "C10", "C11", "C12", "C13", "C14", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "D11", "D12", "D13", "D14", 
                "E1", "E2", "E3", "E4", "E5", "E6", "E7", "E8", "E9", "E10", "E11", "E12", "E13", "E14", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "F13", "F14", 
                "G1", "G2", "G3", "G4", "G5", "G6", "G7", "G8", "G9", "G10", "G11", "G12", "G13", "G14", "H1", "H2", "H3", "H4", "H5", "H6", "H7", "H8", "H9", "H10", "H11", "H12", "H13", "H14",
                "I1", "I2", "I3", "I4", "I5", "I6", "I7", "I8", "I9", "I10", "I11", "I12", "I13", "I14", "J1", "J2", "J3", "J4", "J5", "J6", "J7", "J8", "J9", "J10", "J11", "J12", "J13", "J14",
                "K1", "K2", "K3", "K4", "K5", "K6", "K7", "K8", "K9", "K10", "K11", "K12", "K13", "K14", "L1", "L2", "L3", "L4", "L5", "L6", "L7", "L8", "L9", "L10", "L11", "L12", "L13", "L14",
                "M1", "M2", "M3", "M4", "M5", "M6", "M7", "M8", "M9", "M10", "M11", "M12", "M13", "M14", "N1", "N2", "N3", "N4", "N5", "N6", "N7", "N8", "N9", "N10", "N11", "N12", "N13", "N14"});
        
        poltrona.setFileira(new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N"});
        poltrona.setStatus("DISPONIVEL");
        return poltrona;
    }

    @Test
    @DisplayName("Deve salvar uma poltrona")
    public void savePoltrona() {
        Poltrona poltrona = createPoltrona();

        doReturn(poltrona).when(poltronaRepository.existsByFileiraAndCadeira(poltrona.getFileira(), poltrona.getCadeira()));

        when(poltronaRepository.save(poltrona)).thenReturn(poltrona);

        poltrona = poltronaService.save(poltrona);

        assertThat(poltrona.getId()).isNotNull();
        assertThat(poltrona.getFileira()).isEqualTo(poltrona.getFileira());
        assertThat(poltrona.getCadeira()).isEqualTo(poltrona.getCadeira());
        assertThat(poltrona.getStatus()).isEqualTo(poltrona.getStatus());
    }
}
