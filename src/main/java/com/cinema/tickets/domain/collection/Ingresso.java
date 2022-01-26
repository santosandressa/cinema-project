package com.cinema.tickets.domain.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;

@Document
public class Ingresso {

    @Id
    private String id;

    private Double valor;

    private Boolean meiaEntrada;

    @Size(min= 1, max = 5)
    private Integer quantidade;

    private Exibicao exibicao;

    private Double valorTotal;

    public Ingresso(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Boolean getMeiaEntrada() {
        return meiaEntrada;
    }

    public void setMeiaEntrada(Boolean meiaEntrada) {
        this.meiaEntrada = meiaEntrada;
    }

    public Exibicao getExibicao() {
        return exibicao;
    }

    public void setExibicao(Exibicao exibicao) {
        this.exibicao = exibicao;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
