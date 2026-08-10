package com.example.meuTreino.repository;

import com.example.meuTreino.model.entidade.Exercicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {
    public Optional<Exercicio> findById(Long id);

    public List<Exercicio> findByMusculoAlvoLike(String musculoAlvo);
}
