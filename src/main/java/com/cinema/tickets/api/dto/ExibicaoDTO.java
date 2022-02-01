package com.cinema.tickets.api.dto;

import com.cinema.tickets.domain.collection.Horarios;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.hateoas.RepresentationModel;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import java.util.List;

public class ExibicaoDTO extends RepresentationModel<ExibicaoDTO> {

    private String id;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Schema(example = "2020-01-01")
    @NotNull
    private LocalDate dataExibicao;

    @Schema(example = "14:00")
    @NotEmpty
    private List<Horarios> horarios;

    @Schema(example = "Percy Jackson e o mar de monstros")
    @NotEmpty
    private FilmeDTO filme;

    @Schema(example = "1")
    @NotEmpty
    private SalaDTO sala;


    public ExibicaoDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDataExibicao() {
        return dataExibicao;
    }

    public void setDataExibicao(LocalDate dataExibicao) {
        this.dataExibicao = dataExibicao;
    }

    public List<Horarios> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horarios> horarios) {
        this.horarios = horarios;
    }

    public FilmeDTO getFilme() {
        return filme;
    }

    public void setFilme(FilmeDTO filme) {
        this.filme = filme;
    }

    public SalaDTO getSala() {
        return sala;
    }

    public void setSala(SalaDTO sala) {
        this.sala = sala;
    }
}
