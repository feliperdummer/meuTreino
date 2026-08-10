package com.example.meuTreino.repository;

import com.example.meuTreino.model.entidade.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    public boolean existsByEmail(String email);

    public Optional<Usuario> findByEmail(String email);
}
