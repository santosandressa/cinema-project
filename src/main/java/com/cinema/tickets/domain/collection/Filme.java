package com.cinema.tickets.domain.collection;

import javax.validation.constraints.NotEmpty;

public class Filme {

    private String id;

    @NotEmpty
    private String titulo;

    @NotEmpty
    private String tituloOriginal;

    @NotEmpty
    private String genero;

    @NotEmpty
    private String diretor;

    @NotEmpty
    private String sinopse;

    @NotEmpty
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
