package com.example.meuTreino.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.meuTreino.model.dto.TreinoExercicioDTO;
import com.example.meuTreino.model.entidade.Treino;
import com.example.meuTreino.model.entidade.Usuario;
import com.example.meuTreino.model.dto.TreinoDTO;
import com.example.meuTreino.model.exception.AuthorizationException;
import com.example.meuTreino.model.exception.EntityNotFoundException;
import com.example.meuTreino.repository.TreinoExercicioRepository;
import com.example.meuTreino.repository.TreinoRepository;
import com.example.meuTreino.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class TreinoService {
    @Autowired
    private UsuarioRepository userRepo;
    @Autowired
    private TreinoRepository treinoRepo;
    @Autowired
    private TreinoExercicioRepository trExRepo;
    @Autowired
    private JwtTokenService jwtTokenService;

    public TreinoDTO criarTreino(String token) throws JWTVerificationException,
                                                        EntityNotFoundException
    {
        String subject = jwtTokenService.getSubjectFromToken(
                jwtTokenService.stripeToken(token));
        Usuario usuario = userRepo.findByEmail(subject)
                .orElseThrow(() -> new EntityNotFoundException("usuario nao encontrado"));

        // treino que nao foi concluido
        Treino ultimoTreino = treinoRepo.findFirstByUsuarioOrderByStartTimeDesc(usuario)
                .orElse(null);
        if (ultimoTreino!=null && ultimoTreino.getEndTime()==null) {
            TreinoDTO dto = new TreinoDTO(ultimoTreino);
            dto.setExistente(true);
            return dto;
        }

        Treino novoTreino = new Treino(
                null,
                usuario,
                LocalDate.now(ZoneId.of("America/Sao_Paulo")),
                LocalTime.now(ZoneId.of("America/Sao_Paulo")),
                null
        );
        return new TreinoDTO(treinoRepo.save(novoTreino));
    }

    public void finalizarTreino(String token) throws JWTVerificationException,
                                                                    AuthorizationException,
                                                                    EntityNotFoundException
    {
        String subject = jwtTokenService.getSubjectFromToken(
                jwtTokenService.stripeToken(token));
        Usuario usuario = userRepo.findByEmail(subject)
                .orElseThrow(() -> new EntityNotFoundException("usuario nao encontrado"));
        Treino treino = treinoRepo.findFirstByUsuarioOrderByStartTimeDesc(usuario)
                .orElseThrow(() -> new EntityNotFoundException("treino nao encontrado"));
        if (treino.getEndTime()==null) {
            treino.setEndTime(LocalTime.now(ZoneId.of("America/Sao_Paulo")));
            treinoRepo.save(treino);
        }
    }

    public void deletarTreino(String token, Long treinoId) throws JWTVerificationException,
                                                                  AuthorizationException,
                                                                  EntityNotFoundException
    {
        String subject = jwtTokenService.getSubjectFromToken(
                jwtTokenService.stripeToken(token));
        Usuario usuario = userRepo.findByEmail(subject)
                .orElseThrow(() -> new EntityNotFoundException("usuario nao encontrado"));
        Treino treino = treinoRepo.findById(treinoId)
                .orElseThrow(() -> new EntityNotFoundException("treino nao encontrado"));
        if (treino.getUsuario()!=usuario) {
            throw new AuthorizationException("");
        }
        treinoRepo.delete(treino);
    }

    public List<TreinoDTO> listarTreinos(String token, LocalDate data) throws JWTVerificationException,
                                                                              EntityNotFoundException
    {
        String subject = jwtTokenService.getSubjectFromToken(
                jwtTokenService.stripeToken(token));
        Usuario usuario = userRepo.findByEmail(subject)
                .orElseThrow(() -> new EntityNotFoundException("usuario nao encontrado"));
        if (data==null) {
            return treinoRepo.findAllByUsuario(usuario);
        }
        return treinoRepo.findAllByUsuarioAndData(usuario, data);
    }

    public List<TreinoExercicioDTO> detalharTreino(String token, Long treinoId) throws JWTVerificationException,
                                                                                       AuthorizationException,
                                                                                       EntityNotFoundException
    {
        String subject = jwtTokenService.getSubjectFromToken(
                jwtTokenService.stripeToken(token));
        Usuario usuario = userRepo.findByEmail(subject)
                .orElseThrow(() -> new EntityNotFoundException("usuario nao encontrado"));
        Treino treino = treinoRepo.findById(treinoId)
                .orElseThrow(() -> new EntityNotFoundException("treino nao encontrado"));
        if (treino.getUsuario()!=usuario) {
            throw new AuthorizationException("");
        }
        return trExRepo.findAllByTreinoOrderByTrExIdAscExercicioAsc(treino);
    }
}
