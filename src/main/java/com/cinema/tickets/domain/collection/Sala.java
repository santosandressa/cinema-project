package com.cinema.tickets.domain.collection;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Document
public class Sala {

    @Id
    private String id;

    @NotNull
    private Integer numSala;

    @NotNull
    private Boolean sala3D;

    @NotNull
    private Integer capacidade;

    private List<Poltrona> poltrona = new ArrayList<>();

    public Sala() {
    }

    public String getId() {
        return id;
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
}
