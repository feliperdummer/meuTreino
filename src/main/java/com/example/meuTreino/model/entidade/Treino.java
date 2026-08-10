package com.example.meuTreino.model.entidade;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="treino")
public class Treino {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long treinoId;

    @ManyToOne
    @JoinColumn(name="user_id")
    private Usuario usuario;

    private LocalDate data;

    @Column(name="start_time")
    private LocalTime startTime;

    @Column(name="end_time")
    private LocalTime endTime;

    public Treino() {}

    public Treino(Long treinoId, Usuario usuario, LocalDate data, LocalTime start, LocalTime endTime) {
        this.treinoId = treinoId;
        this.usuario = usuario;
        this.data = data;
        this.startTime = start;
        this.endTime = endTime;
    }

    public Long getTreinoId() {
        return treinoId;
    }

    public void setTreinoId(Long treinoId) {
        this.treinoId = treinoId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStart(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() { return endTime; }

    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
}
