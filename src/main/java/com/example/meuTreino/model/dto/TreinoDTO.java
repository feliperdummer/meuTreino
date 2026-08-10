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

    public TreinoDTO() {}

    public TreinoDTO(Treino treino) {
        this.treinoId = treino.getTreinoId();
        this.usuario = new UsuarioDTO(treino.getUsuario());
        this.data = treino.getData();
        this.startTime = treino.getStartTime();
        this.endTime = treino.getEndTime();
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

    public LocalTime getStartTim() {
        return startTime;
    }

    public void setStart(LocalTime start) {
        this.startTime = start;
    }

    public LocalTime getEnd() {
        return endTime;
    }

    public void setEnd(LocalTime end) {
        this.endTime = end;
    }
}
