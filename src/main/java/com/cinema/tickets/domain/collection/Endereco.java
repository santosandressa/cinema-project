package com.cinema.tickets.domain.collection;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;

public class Endereco {

    @Schema(description = "Rua do endereço", example = "Rua Rio Jacutinga", required = true)
    @NotBlank
    private String rua;

    @Schema(description = "Cep do endereço", example = "87043-643", required = true)
    @NotBlank
    private String cep;

    @Schema(description = "Numero do endereço", example = "292", required = true)
    @NotBlank
    private String numero;

    @Schema(description = "Complemento  do endereço", example = "casa")
    private String complemento;

    @Schema(description = "Bairro do endereço", example = "Jardim Pinheiros II", required = true)
    @NotBlank
    private String bairro;

    @Schema(description = "Cidade do endereço", example = "Maringá", required = true)
    @NotBlank
    private String cidade;

    @Schema(description = "Estado do endereço", example = "PR", required = true)
    @NotBlank
    private String estado;

    public Endereco() {

    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
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

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
