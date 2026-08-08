package com.example.meuTreino.service;

import com.example.meuTreino.model.Treino;
import com.example.meuTreino.model.Usuario;
import com.example.meuTreino.model.dto.TreinoDTO;
import com.example.meuTreino.repository.ExercicioRepository;
import com.example.meuTreino.repository.TreinoRepository;
import com.example.meuTreino.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class TreinoService {
    private final UsuarioRepository userRepo;
    private final ExercicioRepository exRepo;
    private final TreinoRepository treinoRepo;

    public TreinoService(UsuarioRepository userRepo, ExercicioRepository exRepo, TreinoRepository treinoRepo) {
        this.userRepo = userRepo;
        this.exRepo = exRepo;
        this.treinoRepo = treinoRepo;
    }

    public Treino salvar(Treino treino) {
        if (isInvalid(treino)) {
            return null;
        }
        return treinoRepo.save(treino);
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
