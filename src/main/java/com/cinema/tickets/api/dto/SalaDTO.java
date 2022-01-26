package com.cinema.tickets.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

public class SalaDTO {

    @Schema(description = "Identificador da sala", example = "61d742af8769e66ebac6c293")
    private String id;

    @Schema(description = "Numeração da sala", example = "1")
    @NotNull
    private Integer numSala;

    @Schema(description = "Boolean que indica se a sala tem sessão 3D", example = "true")
    @NotNull
    private Boolean sala3D;

    @Schema(description = "Capacidade da sala", example = "216")
    @NotNull
    private Integer capacidade;

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

}
