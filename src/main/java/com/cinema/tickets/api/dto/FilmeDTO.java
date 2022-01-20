package com.cinema.tickets.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;


public class FilmeDTO {

    @Schema(description = "Identificador único do filme")
    private String id;

    @Schema(description = "Nome do filme", example = "O Poderoso Chefão")
    private String titulo;

    @Schema(description = "The GodFather" )
    private String tituloOriginal;

    @Schema(description = "Genero", example = "Ação")
    private String genero;

    @Schema(description = "Diretor", example = "Francis Ford Coppola")
    private String diretor;

    @Schema(description = "Sinopse", example = "Um homem que se torna o mestre do crime")
    private String sinopse;

    @Schema(description = "Duração", example = "175min")
    private String duracao;

    public FilmeDTO() {
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
