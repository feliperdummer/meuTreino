package com.example.meuTreino.model.dto;

import com.example.meuTreino.model.Usuario;

public class UsuarioDTO {
    private Long userId;
    private String email;

    public UsuarioDTO() {}

    public UsuarioDTO(Usuario usuario) {
        this.userId = usuario.getUserId();
        this.email = usuario.getEmail();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
