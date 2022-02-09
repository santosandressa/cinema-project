package com.cinema.tickets.domain.collection;

import io.swagger.v3.oas.annotations.media.Schema;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class Endereco {


    @Schema(description = "Cep do endereço", example = "87043-643", required = true)
    @NotBlank
    @NotNull
    private String cep;


    @Schema(description = "Rua do endereço", example = "Rua Rio Jacutinga")
    private String logradouro;

    @Schema(description = "Numero do endereço", example = "292")
    @NotBlank
    private String numero;

    @Schema(description = "Complemento  do endereço")
    @NotBlank
    private String complemento;

    @Schema(description = "Bairro do endereço", example = "Jardim Pinheiros II")
    private String bairro;

    @Schema(description = "Cidade do endereço", example = "Maringá")
    private String localidade;

    @Schema(description = "Estado do endereço", example = "PR")
    private String uf;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}


