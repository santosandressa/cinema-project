package com.cinema.tickets.domain.collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

@Document
public class Poltrona {

    @Id
    private String id;

    @NotEmpty
    private String cadeira;

    @NotEmpty
    private String fileira;

    @NotEmpty
    private String status;

    @NotEmpty
    private String especiais;

    public Poltrona(){}

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
