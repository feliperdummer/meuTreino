package com.example.meuTreino.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.meuTreino.model.entidade.Treino;
import com.example.meuTreino.model.entidade.Usuario;
import com.example.meuTreino.model.dto.TreinoDTO;
import com.example.meuTreino.repository.ExercicioRepository;
import com.example.meuTreino.repository.TreinoRepository;
import com.example.meuTreino.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class TreinoService {
    @Autowired
    private final UsuarioRepository userRepo;
    @Autowired
    private final ExercicioRepository exRepo;
    @Autowired
    private final TreinoRepository treinoRepo;
    @Autowired
    private final JwtTokenService jwtTokenService;

    public TreinoService(UsuarioRepository userRepo, ExercicioRepository exRepo, TreinoRepository treinoRepo,
                        JwtTokenService jwtTokenService)
    {
        this.userRepo = userRepo;
        this.exRepo = exRepo;
        this.treinoRepo = treinoRepo;
        this.jwtTokenService = jwtTokenService;
    }

    public TreinoDTO criarTreino(String token) {
        String subject;
        try {
            subject = jwtTokenService.getSubjectFromToken(
                    jwtTokenService.stripeToken(token));
        } catch (JWTVerificationException e) {
            return null;
        }
        Usuario usuario = userRepo.findByEmail(subject).orElse(null);
        if (usuario==null) {
            return null;
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

    public Treino editar(Treino treino) {
        if (isInvalid(treino)) {
            return null;
        }
        return treinoRepo.save(treino);
    }

    public void excluir(Treino treino) {
        treinoRepo.delete(treino);
    }

    public List<TreinoDTO> listarPorUsuario(Usuario usuario) {
        return treinoRepo.findAllByUsuario(usuario);
    }

    public List<TreinoDTO> listarPorUsuarioEData(Usuario usuario, LocalDate data) {
        return treinoRepo.findAllByUsuarioAndData(usuario, data);
    }

    public Optional<Treino> encontrePeloId(Long treinoId) {
        return treinoRepo.findById(treinoId);
    }

    private boolean isInvalid(Treino treino) {
        return treino.getUsuario() == null || !userRepo.existsById(treino.getUsuario().getUserId());
    }
}
