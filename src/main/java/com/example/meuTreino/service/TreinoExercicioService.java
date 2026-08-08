package com.example.meuTreino.service;

import com.example.meuTreino.model.Treino;
import com.example.meuTreino.model.TreinoExercicio;
import com.example.meuTreino.model.dto.TreinoExercicioDTO;
import com.example.meuTreino.repository.TreinoExercicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreinoExercicioService {
    private final TreinoExercicioRepository trExRepo;

    public TreinoExercicioService(TreinoExercicioRepository trExRepo) {
        this.trExRepo = trExRepo;
    }

    public TreinoExercicio salvar(TreinoExercicio trEx) {
        if (isInvalid(trEx)) {
            return null;
        }
        return trExRepo.save(trEx);
    }

    public TreinoExercicio editar(TreinoExercicio trEx) {
        if (isInvalid(trEx)) {
            return null;
        }
        return trExRepo.save(trEx);
    }

    public void excluir(TreinoExercicio trEx) {
        trExRepo.delete(trEx);
    }

    public List<TreinoExercicioDTO> encontrePeloTreino(Treino treino) {
        return trExRepo.findAllByTreinoOrderByTrExId(treino);
    }

    private boolean isInvalid(TreinoExercicio trEx) {
        return trEx.getTreino()==null || trEx.getExercicio()==null ||
                trEx.getCarga() < 0 || trEx.getCarga() > 1000 ||
                trEx.getNumeroSerie() < 0 || trEx.getNumeroSerie() > 30 ||
                trEx.getQuantReps() < 0 || trEx.getQuantReps() > 1000;
    }
}
