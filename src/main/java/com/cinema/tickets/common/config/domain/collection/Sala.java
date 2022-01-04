package com.cinema.tickets.common.config.domain.collection;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document
public class Sala {

    @Id
    private String id;

    private Integer numSala;

    private Boolean sala3D;

    private Integer capacidade;

    @DBRef
    private List<Poltrona> poltrona;

    private Integer poltronaReservada;

    private Date data;

    public Sala() {
    }

    public String getId() {
        return id;
    }

    public Sala(String id, Integer numSala, Boolean sala3D, Integer capacidade, List<Poltrona> poltrona, Integer poltronaReservada, Date data) {
        this.id = id;
        this.numSala = numSala;
        this.sala3D = sala3D;
        this.capacidade = capacidade;
        this.poltrona = poltrona;
        this.poltronaReservada = poltronaReservada;
        this.data = data;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getNumSala() {
        return numSala;
    }

    public void setNumSala(Integer numSala) {
        this.numSala = numSala;
    }

    public Boolean getSala3D() {
        return sala3D;
    }

    public void setSala3D(Boolean sala3D) {
        this.sala3D = sala3D;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }

    public List<Poltrona> getPoltrona() {
        return poltrona;
    }

    public void setPoltrona(List<Poltrona> poltrona) {
        this.poltrona = poltrona;
    }

    public Integer getPoltronaReservada() {
        return poltronaReservada;
    }

    public void setPoltronaReservada(Integer poltronaReservada) {
        this.poltronaReservada = poltronaReservada;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
