package com.example.meuTreino.controller;

import com.example.meuTreino.model.Treino;
import com.example.meuTreino.service.TreinoService;
import com.example.meuTreino.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/treino")
public class TreinoController {
    private final TreinoService treinoService;
    private final UsuarioService userService;

    public TreinoController(TreinoService treinoService, UsuarioService userService) {
        this.treinoService = treinoService;
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
}
