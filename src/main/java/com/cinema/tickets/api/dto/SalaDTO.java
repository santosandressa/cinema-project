package com.cinema.tickets.api.dto;

import java.util.List;

public class SalaDTO {

    private String id;

    private Integer numSala;

    private Boolean sala3D;

    private Integer capacidade;

    private List<PoltronaDTO> poltrona;

    public SalaDTO() {

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

    public List<PoltronaDTO> getPoltrona() {
        return poltrona;
    }

    public void setPoltrona(List<PoltronaDTO> poltrona) {
        this.poltrona = poltrona;
    }

}
