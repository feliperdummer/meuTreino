package com.example.meuTreino.controller;

import com.example.meuTreino.model.Treino;
import com.example.meuTreino.model.dto.TreinoDTO;
import com.example.meuTreino.model.dto.TreinoExercicioDTO;
import com.example.meuTreino.service.TreinoExercicioService;
import com.example.meuTreino.service.TreinoService;
import com.example.meuTreino.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/treino")
public class TreinoController {
    private final TreinoService treinoService;
    private final TreinoExercicioService trExService;
    private final UsuarioService userService;

    public TreinoController(TreinoService treinoService, TreinoExercicioService trExService,
                            UsuarioService userService) {
        this.treinoService = treinoService;
        this.trExService = trExService;
        this.userService = userService;
    }

    @PostMapping("/new")
    public ResponseEntity<Long> salvar(@RequestBody Long userId, @RequestBody Treino treino) {
        treino.setUsuario(userService.encontrePeloId(userId).orElse(null));
        Treino novoTreino = treinoService.salvar(treino);
        if (novoTreino==null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(201).body(novoTreino.getTreinoId());
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editar(@RequestBody Long userId, @RequestBody Treino treino) {
        treino.setUsuario(userService.encontrePeloId(userId).orElse(null));
        Treino novoTreino = treinoService.salvar(treino);
        if (novoTreino==null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> excluir(@RequestBody Long userId, @RequestBody Treino treino) {
        treino.setUsuario(userService.encontrePeloId(userId).orElse(null));
        treinoService.excluir(treino);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar/{userId}")
    public ResponseEntity<List<TreinoDTO>> listar(@PathVariable Long userId,
                                                  @RequestParam(value="data", required=false) LocalDate data)
    {
        List<TreinoDTO> returnList;
        if (data==null) {
            returnList = treinoService.listarPorUsuario(userService.encontrePeloId(userId).orElse(null));
        }
        else {
            returnList = treinoService.listarPorUsuarioEData(userService.encontrePeloId(userId).orElse(null),
                    data);
        }
        return ResponseEntity.status(200).body(returnList);
    }

    @GetMapping("detail/{treinoId}")
    public ResponseEntity<List<TreinoExercicioDTO>> detalhar(@PathVariable Long treinoId) {
        Treino treino = treinoService.encontrePeloId(treinoId).orElse(null);
        if (treino==null) {
            return ResponseEntity.badRequest().build();
        }
        List<TreinoExercicioDTO> detalhes = trExService.encontrePeloTreino(treino);
        return ResponseEntity.status(200).body(detalhes);
    }
}
