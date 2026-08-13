package com.example.meuTreino.model.entidade;

import com.example.meuTreino.model.dto.EditTreinoExercicioDTO;
import jakarta.persistence.*;

@Entity
@Table(name="treino_exercicio")
public class TreinoExercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trExId;

    @ManyToOne
    @JoinColumn(name = "treino_id")
    private Treino treino;

    @ManyToOne
    @JoinColumn(name = "exerc_id")
    private Exercicio exercicio;

    private boolean aquecimento;

    @Column(name = "numero_serie")
    private int numeroSerie;
    private int carga;

    @Column(name = "quant_reps")
    private int quantReps;

    public TreinoExercicio() {
    }

    public TreinoExercicio(Long trExId, Treino treino, Exercicio exercicio, boolean aquecimento,
                           int numeroSerie, int carga, int quantReps)
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

    public int getCarga() {
        return carga;
    }

    public void setCarga(int carga) {
        this.carga = carga;
    }

    public boolean getAquecimento() {
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

    public int getQuantReps() {
        return quantReps;
    }

    public void setQuantReps(int quantReps) {
        this.quantReps = quantReps;
    }
}
