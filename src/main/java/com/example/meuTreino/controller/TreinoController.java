package com.example.meuTreino.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.meuTreino.model.dto.TreinoDTO;
import com.example.meuTreino.model.dto.TreinoExercicioDTO;
import com.example.meuTreino.model.exception.AuthorizationException;
import com.example.meuTreino.model.exception.EntityNotFoundException;
import com.example.meuTreino.service.TreinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/treino")
public class TreinoController {
    @Autowired
    private TreinoService treinoService;

    @PostMapping("/new")
    public ResponseEntity<?> criarTreino(@RequestHeader("Authorization") String jwtToken) {
        TreinoDTO treino;
        try {
            treino = treinoService.criarTreino(jwtToken);
        }
        catch (JWTVerificationException jve) {
            return ResponseEntity.status(401).build();
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
        return ResponseEntity.status(201).body(treino);
    }

    @PatchMapping("/{treinoId}/finalizar")
    public ResponseEntity<?> finalizarTreino(@RequestHeader("Authorization") String jwtToken,
                                             @PathVariable Long treinoId)
    {
        try {
            treinoService.finalizarTreino(jwtToken, treinoId);
        }
        catch (JWTVerificationException jve) {
            return ResponseEntity.status(401).build();
        }
        catch (AuthorizationException ae) {
            return ResponseEntity.status(403).build();
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{treinoId}/delete")
    public ResponseEntity<?> excluir(@RequestHeader("Authorization") String jwtToken,
                                     @PathVariable Long treinoId)
    {
        try {
            treinoService.deletarTreino(jwtToken, treinoId);
        }
        catch (JWTVerificationException jve) {
            return ResponseEntity.status(401).build();
        }
        catch (AuthorizationException ae) {
            return ResponseEntity.status(403).build();
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar/{userId}")
    public ResponseEntity<?> listar(@RequestHeader("Authorization") String jwtToken,
                                                  @RequestParam(value="data", required=false) LocalDate data)
    {
        List<TreinoDTO> returnList;
        try {
            returnList = treinoService.listarTreinos(jwtToken, data);
        }
        catch (JWTVerificationException jve) {
            return ResponseEntity.status(401).build();
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
        return ResponseEntity.status(200).body(returnList);
    }

    @GetMapping("{treinoId}/detail")
    public ResponseEntity<?> detalhar(@RequestHeader("Authorization") String jwtToken,
                                                             @PathVariable Long treinoId)
    {
        List<TreinoExercicioDTO> exerciciosList;
        try {
            exerciciosList = treinoService.detalharTreino(jwtToken, treinoId);
        }
        catch (JWTVerificationException jve) {
            return ResponseEntity.status(401).build();
        }
        catch (AuthorizationException ae) {
            return ResponseEntity.status(403).build();
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
        return ResponseEntity.status(200).body(exerciciosList);
    }
}
