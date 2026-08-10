package com.example.meuTreino.model.dto;

import com.example.meuTreino.model.entidade.Exercicio;
import com.example.meuTreino.model.entidade.TreinoExercicio;

public class TreinoExercicioDTO {
    private Long trExId;
    private Long treinoId;
    private Exercicio exercicio;

    private boolean aquecimento;
    private int numeroSerie;
    private int carga;
    private int quantReps;

    public TreinoExercicioDTO() {}

    public TreinoExercicioDTO(TreinoExercicio trEx) {
        this.trExId = trEx.getTrExId();
        this.treinoId = trEx.getTreino().getTreinoId();
        this.exercicio = trEx.getExercicio();
        this.aquecimento = trEx.getAquecimento();
        this.numeroSerie = trEx.getNumeroSerie();
        this.carga = trEx.getCarga();
        this.quantReps = trEx.getQuantReps();
    }

    public Long getTreinoId() {
        return treinoId;
    }

    public void setTreinoId(Long treinoId) {
        this.treinoId = treinoId;
    }

    public Long getTrExId() {
        return trExId;
    }

    public void setTrExId(Long trExId) {
        this.trExId = trExId;
    }

    public Exercicio getExercicio() {
        return exercicio;
    }

    public void setExercicio(Exercicio exercicio) {
        this.exercicio = exercicio;
    }

    public boolean isAquecimento() {
        return aquecimento;
    }

    public void setAquecimento(boolean aquecimento) {
        this.aquecimento = aquecimento;
    }

    public int getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(int numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public int getCarga() {
        return carga;
    }

    public void setCarga(int carga) {
        this.carga = carga;
    }

    public int getQuantReps() {
        return quantReps;
    }

    public void setQuantReps(int quantReps) {
        this.quantReps = quantReps;
    }
}
