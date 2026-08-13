package com.example.meuTreino.model.dto;

public record NovoTreinoExercicioDTO(Long treinoId, Long exercId, boolean aquecimento,
                                     int numeroSerie, int carga, int quantReps) {}
