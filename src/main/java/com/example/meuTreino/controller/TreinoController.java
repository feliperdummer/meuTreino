package com.example.meuTreino.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.meuTreino.model.entidade.Treino;
import com.example.meuTreino.model.entidade.Usuario;
import com.example.meuTreino.model.dto.TreinoDTO;
import com.example.meuTreino.model.dto.TreinoExercicioDTO;
import com.example.meuTreino.model.exception.AuthorizationException;
import com.example.meuTreino.model.exception.TreinoNotFoundException;
import com.example.meuTreino.model.exception.UserNotFoundException;
import com.example.meuTreino.service.AuthService;
import com.example.meuTreino.service.TreinoExercicioService;
import com.example.meuTreino.service.TreinoService;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/treino")
public class TreinoController {
    private final TreinoService treinoService;
    private final TreinoExercicioService trExService;
    private final AuthService authService;

    public TreinoController(TreinoService treinoService,
                            TreinoExercicioService trExService,
                            AuthService authService)
    {
        this.treinoService = treinoService;
        this.trExService = trExService;
        this.authService = authService;
    }

    @PostMapping("/new")
    public ResponseEntity<?> criarTreino(@RequestHeader("Authorization") String jwtToken) {
        TreinoDTO treino;
        try {
            treino = treinoService.criarTreino(jwtToken);
        }
        catch (JWTVerificationException jve) {
            return ResponseEntity.status(401).build();
        }
        catch (UserNotFoundException e) {
            return ResponseEntity.status(404).build();
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
        catch (UserNotFoundException | TreinoNotFoundException e) {
            return ResponseEntity.status(404).build();
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
        catch (UserNotFoundException | TreinoNotFoundException e) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar/{userId}")
    public ResponseEntity<List<TreinoDTO>> listar(@RequestHeader("Authorization") String jwtToken,
                                                  @RequestParam(value="data", required=false) LocalDate data)
    {
        List<TreinoDTO> returnList;
        try {
            returnList = treinoService.listarTreinos(jwtToken, data);
        }
        catch (JWTVerificationException jve) {
            return ResponseEntity.status(401).build();
        }
        catch (UserNotFoundException ue) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(returnList);
    }

    @GetMapping("{treinoId}/detail")
    public ResponseEntity<List<TreinoExercicioDTO>> detalhar(@RequestHeader("Authorization") String jwtToken,
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
        catch (UserNotFoundException | TreinoNotFoundException e) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(exerciciosList);
    }
}
