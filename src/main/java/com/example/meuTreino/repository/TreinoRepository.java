package com.example.meuTreino.repository;

import com.example.meuTreino.model.entidade.Treino;
import com.example.meuTreino.model.entidade.Usuario;
import com.example.meuTreino.model.dto.TreinoDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TreinoRepository extends JpaRepository<Treino, Long> {
    List<TreinoDTO> findAllByUsuario(Usuario usuario);

    List<TreinoDTO> findAllByUsuarioAndData(Usuario usuario, LocalDate data);

    Optional<Treino> findFirstByUsuarioOrderByStartTimeDesc(Usuario usuario);
}
