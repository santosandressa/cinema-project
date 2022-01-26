package com.cinema.tickets.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Size;

public class IngressoDTO {


    private String id;

    @Schema(example = "30.00")
    private Double valor;

    @Schema(example = "true")
    private Boolean meiaEntrada;

    @Schema(description = "Quantidade de 1 a 5", example = "3")
    @Size(max = 5)
    private Integer quantidade;

    @Schema(example = "61eeaaa52ac9a562141577d7")
    private ExibicaoDTO exibicao;

    private Double valorTotal;


    public IngressoDTO() {
    }

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

    public Boolean getMeiaEntrada() {
        return meiaEntrada;
    }

    public void setMeiaEntrada(Boolean meiaEntrada) {
        this.meiaEntrada = meiaEntrada;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public ExibicaoDTO getExibicao() {
        return exibicao;
    }

    public void setExibicao(ExibicaoDTO exibicao) {
        this.exibicao = exibicao;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
