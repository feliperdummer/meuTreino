package com.example.meuTreino.repository;

import com.example.meuTreino.model.entidade.Exercicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {
    List<Exercicio> findByMusculoAlvoLike(String musculoAlvo);
}
