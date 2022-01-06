package com.cinema.tickets.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;

public class PoltronaDTO {

    @Schema(description = "Identificador da poltrona", example = "61d6e4b64027c3715ee97dea")
    private String id;

    @Schema(description = "Identificador da cadeira", example = "A1")
    @NotEmpty
    private String cadeira;

    @Schema(description = "Identificador da fileira", example = "A")
    @NotEmpty
    private String fileira;

    @Schema(description = "Status da poltrona", example = "DISPONIVEL")
    @NotEmpty
    private String status;

    @Schema(description = "Poltronara especial", example = "Normal")
    private String especiais;

    public PoltronaDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCadeira() {
        return cadeira;
    }

    public void setCadeira(String cadeira) {
        this.cadeira = cadeira;
    }

    public String getFileira() {
        return fileira;
    }

    public void setFileira(String fileira) {
        this.fileira = fileira;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEspeciais() {
        return especiais;
    }

    public void setEspeciais(String especiais) {
        this.especiais = especiais;
    }
}
