package com.example.meuTreino.model;

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
    private LocalTime start;
    private LocalTime end;

    public Treino() {}

    public Treino(Long treinoId, Usuario usuario, LocalDate data, LocalTime start, LocalTime end) {
        this.treinoId = treinoId;
        this.usuario = usuario;
        this.data = data;
        this.start = start;
        this.end = end;
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

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() { return end; }

    public void setEnd(LocalTime end) { this.end = end; }
}
