package com.example.meuTreino.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.meuTreino.model.dto.EditTreinoExercicioDTO;
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

    public TreinoExercicioDTO editar(String token, EditTreinoExercicioDTO serie)
        throws JWTVerificationException, AuthorizationException,
                InvalidFieldException, EntityNotFoundException
    {
        if (isInvalid(serie)) { throw new InvalidFieldException(""); }

        String subject = jwtTokenService.getSubjectFromToken(
                jwtTokenService.stripeToken(token));
        Usuario usuario = userRepo.findByEmail(subject)
                .orElseThrow(() -> new EntityNotFoundException("usuario nao encontrado"));
        Treino treino = treinoRepo.findById(serie.treinoId())
                .orElseThrow(() -> new EntityNotFoundException("treino nao encontrado"));
        if (treino.getUsuario()!=usuario) { throw new AuthorizationException(""); }

        TreinoExercicio serieExistente = trExRepo.findById(serie.trExId())
                .orElseThrow(() -> new EntityNotFoundException("serie nao encontrada"));

        updateTrExIfNotNull(serieExistente, serie);

        return new TreinoExercicioDTO(trExRepo.save(serieExistente));
    }

    public void deletarSerie(String token, Long trExId) throws EntityNotFoundException,
                                                          JWTVerificationException,
                                                          AuthorizationException
    {
        TreinoExercicio serie = trExRepo.findById(trExId)
                .orElseThrow(() -> new EntityNotFoundException("serie nao encontrada"));
        Treino treino = treinoRepo.findById(serie.getTreino().getTreinoId())
                .orElseThrow(() -> new EntityNotFoundException("treino nao encontrado"));

        String subject = jwtTokenService.getSubjectFromToken(
                jwtTokenService.stripeToken(token));
        Usuario usuario = userRepo.findByEmail(subject)
                .orElseThrow(() -> new EntityNotFoundException("usuario nao encontrado"));
        if (treino.getUsuario()!=usuario) { throw new AuthorizationException(""); }

        trExRepo.delete(serie);
    }

    private boolean isInvalid(NovoTreinoExercicioDTO serie) {
        return serie.treinoId()==null
                || serie.numeroSerie() <= 0 || serie.numeroSerie() > 30
                || serie.carga() < 0 || serie.carga() > 1000
                || serie.quantReps() <= 0 || serie.quantReps() > 100;
    }

    private boolean isInvalid(EditTreinoExercicioDTO serie) {
        return serie.treinoId()==null || serie.trExId()==null
                || (serie.numeroSerie()!=null && (serie.numeroSerie() <= 0 || serie.numeroSerie() > 30))
                || (serie.carga()!=null && (serie.carga() < 0 || serie.carga() > 1000))
                || (serie.quantReps()!=null && (serie.quantReps() <= 0 || serie.quantReps() > 100));
    }

    private void updateTrExIfNotNull(TreinoExercicio serieExistente,
                                     EditTreinoExercicioDTO serieEditada)
    {
        Exercicio novoEx = exRepo.findById(serieEditada.exercId())
                .orElse(serieExistente.getExercicio());
        boolean aquecimento = serieEditada.aquecimento()==null
                ? serieExistente.getAquecimento()
                : serieEditada.aquecimento();
        int numeroSerie = serieEditada.numeroSerie()==null
                ? serieExistente.getNumeroSerie()
                : serieEditada.numeroSerie();
        int carga = serieEditada.carga()==null
                ? serieExistente.getCarga()
                : serieEditada.carga();
        int quantReps = serieEditada.quantReps()==null
                ? serieExistente.getQuantReps()
                : serieEditada.quantReps();
        serieExistente.setExercicio(novoEx);
        serieExistente.setAquecimento(aquecimento);
        serieExistente.setNumeroSerie(numeroSerie);
        serieExistente.setCarga(carga);
        serieExistente.setQuantReps(quantReps);
    }
}
