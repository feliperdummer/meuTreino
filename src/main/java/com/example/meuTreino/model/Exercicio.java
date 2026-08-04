package com.example.meuTreino.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Exercicio {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long exercId;

    private String nome;
    private String musculoAlvo;

    public Exercicio() {}

    public Exercicio(Long exercId, String nome, String musculoAlvo) {
        this.exercId = exercId;
        this.nome = nome;
        this.musculoAlvo = musculoAlvo;
    }

    public Long getExercId() {
        return exercId;
    }

    public void setExercId(Long exercId) {
        this.exercId = exercId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMusculoAlvo() {
        return musculoAlvo;
    }

    public void setMusculoAlvo(String musculoAlvo) {
        this.musculoAlvo = musculoAlvo;
    }
}
