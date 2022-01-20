package com.cinema.tickets.domain.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;
import java.util.Date;

@Document
public class Ingresso {

    @Id
    private String id;

    private Double valor;

    @Size(max = 5)
    private Integer quantidade;

    private Sala sala;

    private Filme filme;

}
