package com.cinema.tickets.common.config.domain.collection;

import javax.validation.constraints.Size;
import java.util.Date;

public class Ingresso {

    private String id;

    private Date data;

    private Date horario;

    private Double valor;

    @Size(max = 5)
    private Integer quantidade;

    private Sala sala;

    private Filme filme;

}
