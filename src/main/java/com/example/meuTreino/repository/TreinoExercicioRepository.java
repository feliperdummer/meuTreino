package com.example.meuTreino.repository;

import com.example.meuTreino.model.Exercicio;
import com.example.meuTreino.model.Treino;
import com.example.meuTreino.model.TreinoExercicio;
import com.example.meuTreino.model.dto.TreinoExercicioDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreinoExercicioRepository extends JpaRepository<TreinoExercicio, Long> {
    public List<TreinoExercicioDTO> findAllByTreinoOrderByTrExId(Treino treino);

    public List<TreinoExercicio> findByExercicio(Exercicio exercicio);
}
