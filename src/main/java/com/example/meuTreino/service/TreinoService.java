package com.example.meuTreino.service;

import com.example.meuTreino.model.Treino;
import com.example.meuTreino.model.Usuario;
import com.example.meuTreino.repository.ExercicioRepository;
import com.example.meuTreino.repository.TreinoRepository;
import com.example.meuTreino.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    public boolean excluir(Treino treino) {
        treinoRepo.delete(treino);
        return true;
    }

    public List<Treino> listarPorUsuario(Usuario usuario) {
        return treinoRepo.findAllByUsuario(usuario);
    }

    public List<Treino> listarPorData(LocalDate data) {
        return treinoRepo.findAllByData(data);
    }

    private boolean isInvalid(Treino treino) {
        return treino.getUsuario() == null || !userRepo.existsById(treino.getUsuario().getUserId())
                || treino.getData() == null || treino.getData().isBefore(LocalDate.now())
                || treino.getHorario() == null || treino.getHorario().isBefore(LocalTime.now());
    }
}
