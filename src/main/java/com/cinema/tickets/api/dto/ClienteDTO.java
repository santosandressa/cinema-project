package com.cinema.tickets.api.dto;

import com.cinema.tickets.domain.collection.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class ClienteDTO {

    @Schema(description = "Identificador único do cliente")
    private String id;

    @Schema(description = "Nome do cliente", example = "Ester Isadora Melissa Gomes", required = true)
    @NotEmpty
    private String nome;

    @Schema(description = "Cpf do cliente", example = "042.952.896-55", required = true)
    @CPF
    @NotEmpty
    private String cpf;

    @Schema(description = "Data de nascimento do cliente", example = "24/05/2001", required = true)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @NotEmpty
    private String dataNascimento;

    @Schema(description = "Celular do cliente", example = "(44) 98244-7112", required = true)
    @NotEmpty
    private String celular;

    @Schema(description = "Email do cliente", example = "esterisadoramelissagomess@dprf.gov.br", required = true)
    @Email
    @NotEmpty
    private String email;

    @Schema(description = "Senha do cliente", example = "sOu5JTNBQ3", required = true)
    @NotEmpty
    @Size(min = 6, max = 12)
    private String senha;

    @Schema(description = "Endereço do cliente")
    private Endereco endereco;

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
