package com.example.meuTreino.repository;

import com.example.meuTreino.model.entidade.Treino;
import com.example.meuTreino.model.entidade.Usuario;
import com.example.meuTreino.model.dto.TreinoDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TreinoRepository extends JpaRepository<Treino, Long> {
    public List<TreinoDTO> findAllByUsuario(Usuario usuario);

    public List<TreinoDTO> findAllByUsuarioAndData(Usuario usuario, LocalDate data);
}
