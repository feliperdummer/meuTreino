package com.example.meuTreino.repository;

import com.example.meuTreino.model.Treino;
import com.example.meuTreino.model.Usuario;
import com.example.meuTreino.model.dto.TreinoDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TreinoRepository extends JpaRepository<Treino, Long> {
    public List<TreinoDTO> findAllByUsuario(Usuario usuario);

    public List<TreinoDTO> findAllByUsuarioAndData(Usuario usuario, LocalDate data);
}
