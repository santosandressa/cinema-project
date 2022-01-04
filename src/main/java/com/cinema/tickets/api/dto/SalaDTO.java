package com.cinema.tickets.api.dto;

import java.util.Date;
import java.util.List;

public class SalaDTO {

    private Integer numSala;

    private Boolean sala3D;

    private Integer capacidade;

    private List<PoltronaDTO> poltrona;

    private Integer poltronaReservada;

    private Date data;

    public SalaDTO() {

    }

    public SalaDTO(Integer numSala, Boolean sala3D, Integer capacidade, List<PoltronaDTO> poltrona, Integer poltronaReservada, Date data) {
        this.numSala = numSala;
        this.sala3D = sala3D;
        this.capacidade = capacidade;
        this.poltrona = poltrona;
        this.poltronaReservada = poltronaReservada;
        this.data = data;
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
