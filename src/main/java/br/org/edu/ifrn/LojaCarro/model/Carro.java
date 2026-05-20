package br.org.edu.ifrn.LojaCarro.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Entity
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O modelo é obrigatório")
    @Size(max = 50, message = "O modelo não pode ter mais de 50 caracteres")
    private String modelo;

    @Min(value = 1886, message = "Ano inválido, o primeiro carro foi criado em 1886")
    @Max(value = 2100, message = "Ano inválido, muito no futuro")
    private int ano;

    // getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}
