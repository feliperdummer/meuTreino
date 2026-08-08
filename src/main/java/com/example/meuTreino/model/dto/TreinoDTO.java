package com.example.meuTreino.model.dto;

import com.example.meuTreino.model.Treino;
import com.example.meuTreino.model.Usuario;
import com.example.meuTreino.repository.TreinoRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public class TreinoDTO {
    private Long treinoId;
    private UsuarioDTO usuario;
    private LocalDate data;
    private LocalTime start;
    private LocalTime end;

    public TreinoDTO() {}

    public TreinoDTO(Treino treino) {
        this.treinoId = treino.getTreinoId();
        this.usuario = new UsuarioDTO(treino.getUsuario());
        this.data = treino.getData();
        this.start = treino.getStart();
        this.end = treino.getEnd();
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

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }
}
