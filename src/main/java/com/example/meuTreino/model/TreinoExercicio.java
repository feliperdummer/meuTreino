package com.example.meuTreino.model;

import jakarta.persistence.*;

@Entity
public class TreinoExercicio {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long trExId;

    @ManyToOne
    @JoinColumn(name="treino_id")
    private Treino treino;

    @ManyToOne
    @JoinColumn(name="exerc_id")
    private Exercicio exercicio;

    private Boolean aquecimento;
    private Integer numeroSerie;
    private Integer carga;
    private Integer quantReps;

    public TreinoExercicio() {}

    public TreinoExercicio(Long trExId, Treino treino, Exercicio exercicio, Boolean aquecimento,
                           Integer numeroSerie, Integer carga, Integer quantReps)
    {
        this.trExId = trExId;
        this.treino = treino;
        this.exercicio = exercicio;
        this.aquecimento = aquecimento;
        this.numeroSerie = numeroSerie;
        this.carga = carga;
        this.quantReps = quantReps;
    }

    public Long getTrExId() {
        return trExId;
    }

    public void setTrExId(Long trExId) {
        this.trExId = trExId;
    }

    public Treino getTreino() {
        return treino;
    }

    public void setTreino(Treino treino) {
        this.treino = treino;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public Integer getCarga() {
        return carga;
    }

    public void setCarga(Integer carga) {
        this.carga = carga;
    }

    public Boolean getAquecimento() {
        return aquecimento;
    }

    public void setAquecimento(Boolean aquecimento) {
        this.aquecimento = aquecimento;
    }

    public Integer getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(Integer numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public Integer getQuantReps() {
        return quantReps;
    }

    public void setQuantReps(Integer quantReps) {
        this.quantReps = quantReps;
    }
}
