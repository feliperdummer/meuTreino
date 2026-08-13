package com.example.meuTreino.model.dto;

public record EditTreinoExercicioDTO(Long trExId, Long treinoId, Long exercId,
                                     Boolean aquecimento, Integer numeroSerie, Integer carga,
                                     Integer quantReps) {}
