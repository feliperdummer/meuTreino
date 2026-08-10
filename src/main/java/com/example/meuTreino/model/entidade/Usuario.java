package com.example.meuTreino.model.entidade;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String email;

    @ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
    @JoinTable(name="cargos",
    joinColumns = @JoinColumn(name="user_id"),
    inverseJoinColumns = @JoinColumn(name="cargo_id"))
    private List<Cargo> cargos;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String senha;

    @Column(name="data_nasc", nullable = false)
    private LocalDate dataNasc;

    public Usuario() {}

    public Usuario(Long userId, String nome, String email, String senha,
                   LocalDate dataNasc)
    {
        this.userId = userId;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataNasc = dataNasc;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargo(List<Cargo> cargos) {
        this.cargos = cargos;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }
}
