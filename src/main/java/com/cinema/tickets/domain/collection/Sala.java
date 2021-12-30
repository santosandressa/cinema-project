package com.cinema.tickets.domain.collection;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Sala {

    private String id;

    private Integer numSala;

    private Boolean sala3D;

    private Integer capacidade;

    private List<Poltrona> poltrona;

    private Integer poltronaReservada;

    private Date data;

    public Sala() {
    }
}
