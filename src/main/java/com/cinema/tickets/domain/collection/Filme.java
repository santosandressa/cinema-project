package com.cinema.tickets.domain.collection;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Document(collection = "filme")
public class Filme {

    @Schema(description = "Identificador único do filme")
    private String id;

    @Schema(description = "Nome do filme", example = "O Poderoso Chefão")
    private String titulo;

    @Schema(description = "Nome Original", example = "The Godfather")
    private String tituloOriginal;

    @Schema(description = "Genero", example = "Ação")
    private String genero;

    @Schema(description = "Diretor", example = "Francis Ford Coppola")
    private String diretor;

    @Schema(description = "Sinopse", example = "Um homem que se torna o mestre do crime")
    private String sinopse;

    @Schema(description = "Duração", example = "175 min")
    private String duracao;

    public Filme(String id, String titulo, String tituloOriginal, String genero, String diretor, String sinopse, String duracao) {
        this.id = id;
        this.titulo = titulo;
        this.tituloOriginal = tituloOriginal;
        this.genero = genero;
        this.diretor = diretor;
        this.sinopse = sinopse;
        this.duracao = duracao;
    }

    public Filme() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTituloOriginal() {
        return tituloOriginal;
    }

    public void setTituloOriginal(String tituloOriginal) {
        this.tituloOriginal = tituloOriginal;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDiretor() {
        return diretor;
    }

    public void setDiretor(String diretor) {
        this.diretor = diretor;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }
}
