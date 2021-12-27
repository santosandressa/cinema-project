package com.cinema.tickets.api.dto;

import com.cinema.tickets.domain.collection.Endereco;
import org.springframework.format.annotation.DateTimeFormat;

public class ClienteDTO {

    private String id;

    private String nome;

    private String cpf;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private String dataNascimento;

    private String celular;

    private String email;

    private String senha;

    private Endereco endereco;

    public ClienteDTO(String id, String nome, String cpf, String dataNascimento, String celular, String email, String senha, Endereco endereco) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.celular = celular;
        this.email = email;
        this.senha = senha;
        this.endereco = endereco;
    }

    public ClienteDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
