package com.cinema.tickets.domain.collection;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import javax.validation.constraints.NotEmpty;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Document
public class Exibicao implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @NotEmpty
    private LocalDate dataExibicao;

    @NotEmpty
    private Filme filme;

    @NotEmpty
    private Sala sala;

    @NotEmpty
    private List<Horarios> horarios;

    public Exibicao() {
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

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public Sala getSala() {
        return sala;
    }

    public void setSala(Sala sala) {
        this.sala = sala;
    }

    public List<Horarios> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<Horarios> horarios) {
        this.horarios = horarios;
    }
}
