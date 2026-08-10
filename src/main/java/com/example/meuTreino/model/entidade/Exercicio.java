package com.example.meuTreino.model.entidade;

import jakarta.persistence.*;

@Entity
@Table(name="exercicio")
public class Exercicio {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long exercId;

    private String nome;

    @Column(name="musculo_alvo", nullable = false)
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
