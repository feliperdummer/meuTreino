package com.example.meuTreino.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Treino {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long treinoId;

    @ManyToOne
    @JoinColumn(name="user_id")
    private Usuario usuario;

    private LocalDate data;
    private LocalTime horario;

    public Treino() {}

    public Treino(Long treinoId, Usuario usuario, LocalDate data, LocalTime horario) {
        this.treinoId = treinoId;
        this.usuario = usuario;
        this.data = data;
        this.horario = horario;
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

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }
}
