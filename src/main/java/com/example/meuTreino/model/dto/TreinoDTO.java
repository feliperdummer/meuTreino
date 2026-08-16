package com.example.meuTreino.model.dto;

import com.example.meuTreino.model.entidade.Treino;

import java.time.LocalDate;
import java.time.LocalTime;

public class TreinoDTO {
    private Long treinoId;
    private UsuarioDTO usuario;
    private LocalDate data;
    private LocalTime startTime;
    private LocalTime endTime;

    // caso o treino ja existe e nao tenha sido finalizado,
    // o front lida com esse caso na hora que recebe o resultado
    // da requisicao
    private boolean existente;

    public TreinoDTO() {}

    public TreinoDTO(Treino treino) {
        this.treinoId = treino.getTreinoId();
        this.usuario = new UsuarioDTO(treino.getUsuario());
        this.data = treino.getData();
        this.startTime = treino.getStartTime();
        this.endTime = treino.getEndTime();
        existente = false;
    }

    public Long getTreinoId() {
        return treinoId;
    }

    public void setTreinoId(Long treinoId) {
        this.treinoId = treinoId;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
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

    public void setStartTime(LocalTime start) {
        this.startTime = start;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime end) {
        this.endTime = end;
    }

    public boolean isExistente() {
        return existente;
    }

    public void setExistente(boolean existente) {
        this.existente = existente;
    }
}
