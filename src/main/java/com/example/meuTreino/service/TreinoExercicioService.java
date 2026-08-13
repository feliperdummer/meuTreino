package com.example.meuTreino.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.meuTreino.model.dto.NovoTreinoExercicioDTO;
import com.example.meuTreino.model.entidade.Exercicio;
import com.example.meuTreino.model.entidade.Treino;
import com.example.meuTreino.model.entidade.TreinoExercicio;
import com.example.meuTreino.model.dto.TreinoExercicioDTO;
import com.example.meuTreino.model.entidade.Usuario;
import com.example.meuTreino.model.exception.*;
import com.example.meuTreino.repository.ExercicioRepository;
import com.example.meuTreino.repository.TreinoExercicioRepository;
import com.example.meuTreino.repository.TreinoRepository;
import com.example.meuTreino.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TreinoExercicioService {
    @Autowired
    private TreinoExercicioRepository trExRepo;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private UsuarioRepository userRepo;
    @Autowired
    private TreinoRepository treinoRepo;
    @Autowired
    private ExercicioRepository exRepo;

    public TreinoExercicioDTO novaSerie(String token, NovoTreinoExercicioDTO serie)
            throws JWTVerificationException, AuthorizationException,
                   InvalidFieldException, EntityNotFoundException
    {
        String subject = jwtTokenService.getSubjectFromToken(
                jwtTokenService.stripeToken(token));
        Usuario usuario = userRepo.findByEmail(subject)
                .orElseThrow(() -> new EntityNotFoundException("usuario nao encontrado"));
        Treino treino = treinoRepo.findById(serie.treinoId())
                .orElseThrow(() -> new EntityNotFoundException("treino nao encontrado"));
        Exercicio exercicio = exRepo.findById(serie.exercId())
                .orElseThrow(() -> new EntityNotFoundException("exercicio nao encontrado"));

        if (treino.getUsuario()!=usuario) { throw new AuthorizationException(""); }
        if (isInvalid(serie)) { throw new InvalidFieldException(""); }

        TreinoExercicio novaSerie = new TreinoExercicio(
                null,
                treino,
                exercicio,
                serie.aquecimento(),
                serie.numeroSerie(),
                serie.carga(),
                serie.quantReps()
        );

        return new TreinoExercicioDTO(trExRepo.save(novaSerie));
    }

    public TreinoExercicioDTO editar(String token, TreinoExercicioDTO serie)
        throws JWTVerificationException, AuthorizationException,
                InvalidFieldException, EntityNotFoundException
    {
        if (isInvalid(serie)) { throw new InvalidFieldException(""); }

        String subject = jwtTokenService.getSubjectFromToken(
                jwtTokenService.stripeToken(token));
        Treino treino = treinoRepo.findById(serie.getTreinoId())
                .orElseThrow(() -> new EntityNotFoundException("treino nao encontrado"));
        Usuario usuario = userRepo.findByEmail(subject)
                .orElseThrow(() -> new EntityNotFoundException("usuario nao encontrado"));
        if (treino.getUsuario()!=usuario) { throw new AuthorizationException(""); }
        TreinoExercicio serieExistente = trExRepo.findById(serie.getTrExId())
                .orElseThrow(() -> new EntityNotFoundException("serie nao encontrada"));

        serieExistente.setExercicio(serie.getExercicio());
        serieExistente.setAquecimento(serie.isAquecimento());
        serieExistente.setNumeroSerie(serie.getNumeroSerie());
        serieExistente.setCarga(serie.getCarga());
        serieExistente.setQuantReps(serie.getQuantReps());

        return new TreinoExercicioDTO(trExRepo.save(serieExistente));
    }

    public void excluir(TreinoExercicio trEx) {
        return;
    }

    private boolean isInvalid(NovoTreinoExercicioDTO serie) {
        return serie.treinoId()==null
                || serie.numeroSerie() <= 0 || serie.numeroSerie() > 30
                || serie.carga() < 0 || serie.carga() > 1000
                || serie.quantReps() <= 0 || serie.quantReps() > 100;
    }

    private boolean isInvalid(TreinoExercicioDTO serie) {
        return serie.getTreinoId()==null || serie.getExercicio()==null
                || serie.getNumeroSerie() <= 0 || serie.getNumeroSerie() > 30
                || serie.getCarga() < 0 || serie.getCarga() > 1000
                || serie.getQuantReps() <= 0 || serie.getQuantReps() > 100;
    }

}
