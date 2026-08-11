package com.example.meuTreino.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.meuTreino.model.dto.TreinoExercicioDTO;
import com.example.meuTreino.model.entidade.Treino;
import com.example.meuTreino.model.entidade.Usuario;
import com.example.meuTreino.model.dto.TreinoDTO;
import com.example.meuTreino.model.exception.AuthorizationException;
import com.example.meuTreino.model.exception.TreinoNotFoundException;
import com.example.meuTreino.model.exception.UserNotFoundException;
import com.example.meuTreino.repository.ExercicioRepository;
import com.example.meuTreino.repository.TreinoExercicioRepository;
import com.example.meuTreino.repository.TreinoRepository;
import com.example.meuTreino.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TreinoService {
    private final UsuarioRepository userRepo;
    private final ExercicioRepository exRepo;
    private final TreinoRepository treinoRepo;
    private final TreinoExercicioRepository trExRepo;
    private final JwtTokenService jwtTokenService;

    public TreinoService(UsuarioRepository userRepo, ExercicioRepository exRepo,
                         TreinoRepository treinoRepo, TreinoExercicioRepository trExRepo,
                         JwtTokenService jwtTokenService)
    {
        this.userRepo = userRepo;
        this.exRepo = exRepo;
        this.treinoRepo = treinoRepo;
        this.trExRepo = trExRepo;
        this.jwtTokenService = jwtTokenService;
    }

    public TreinoDTO criarTreino(String token) throws JWTVerificationException,
                                                        UserNotFoundException
    {
        String subject =jwtTokenService.getSubjectFromToken(
                jwtTokenService.stripeToken(token));
        Usuario usuario = userRepo.findByEmail(subject)
                .orElseThrow(UserNotFoundException::new);
        Treino novoTreino = new Treino(
                null,
                usuario,
                LocalDate.now(ZoneId.of("America/Sao_Paulo")),
                LocalTime.now(ZoneId.of("America/Sao_Paulo")),
                null
        );
        return new TreinoDTO(treinoRepo.save(novoTreino));
    }

    public void finalizarTreino(String token, Long treinoId) throws UserNotFoundException,
                                                                    TreinoNotFoundException,
                                                                    JWTVerificationException,
                                                                    AuthorizationException
    {
        String subject = jwtTokenService.getSubjectFromToken(
                jwtTokenService.stripeToken(token));
        Usuario usuario = userRepo.findByEmail(subject)
                .orElseThrow(UserNotFoundException::new);
        Treino treino = treinoRepo.findById(treinoId)
                .orElseThrow(TreinoNotFoundException::new);
        if (treino.getUsuario()!=usuario) {
            throw new AuthorizationException("");
        }
        treino.setEndTime(LocalTime.now(ZoneId.of("America/Sao_Paulo")));
        treinoRepo.save(treino);
    }

    public void deletarTreino(String token, Long treinoId) throws JWTVerificationException,
                                                                  TreinoNotFoundException,
                                                                  UserNotFoundException,
                                                                  AuthorizationException
    {
        String subject = jwtTokenService.getSubjectFromToken(
                jwtTokenService.stripeToken(token));
        Usuario usuario = userRepo.findByEmail(subject)
                .orElseThrow(UserNotFoundException::new);
        Treino treino = treinoRepo.findById(treinoId)
                .orElseThrow(TreinoNotFoundException::new);
        if (treino.getUsuario()!=usuario) {
            throw new AuthorizationException("");
        }
        treinoRepo.delete(treino);
    }

    public List<TreinoDTO> listarTreinos(String token, LocalDate data) throws JWTVerificationException,
                                                                              UserNotFoundException
    {
        String subject = jwtTokenService.getSubjectFromToken(
                jwtTokenService.stripeToken(token));
        Usuario usuario = userRepo.findByEmail(subject)
                .orElseThrow(UserNotFoundException::new);
        if (data==null) {
            return treinoRepo.findAllByUsuario(usuario);
        }
        return treinoRepo.findAllByUsuarioAndData(usuario, data);
    }

    public List<TreinoExercicioDTO> detalharTreino(String token, Long treinoId) throws JWTVerificationException,
                                                                                         UserNotFoundException,
                                                                                         TreinoNotFoundException,
                                                                                         AuthorizationException
    {
        String subject = jwtTokenService.getSubjectFromToken(
                jwtTokenService.stripeToken(token));
        Usuario usuario = userRepo.findByEmail(subject)
                .orElseThrow(UserNotFoundException::new);
        Treino treino = treinoRepo.findById(treinoId).orElseThrow(TreinoNotFoundException::new);
        if (treino.getUsuario()!=usuario) {
            throw new AuthorizationException("");
        }
        return trExRepo.findAllByTreinoOrderByTrExId(treino);
    }

    public Optional<Treino> encontrePeloId(Long treinoId) {
        return treinoRepo.findById(treinoId);
    }
}
