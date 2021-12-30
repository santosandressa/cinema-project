package com.cinema.tickets.api.dto;

import javax.validation.constraints.NotEmpty;

public class PoltronaDTO {

    private String id;

    @NotEmpty
    private String cadeira;

    @NotEmpty
    private String fileira;

    @NotEmpty
    private String status;


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
}
