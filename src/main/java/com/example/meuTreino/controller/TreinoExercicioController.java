package com.example.meuTreino.controller;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.meuTreino.model.dto.TreinoExercicioDTO;
import com.example.meuTreino.model.dto.NovoTreinoExercicioDTO;
import com.example.meuTreino.model.exception.*;
import com.example.meuTreino.service.TreinoExercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/serie")
public class TreinoExercicioController {
    @Autowired
    private TreinoExercicioService trExService;

    @PostMapping("/new")
    public ResponseEntity<?> novaSerie(@RequestHeader("Authorization") String jwtToken,
                                       @RequestBody NovoTreinoExercicioDTO serie)
    {
        TreinoExercicioDTO novaSerie;
        try {
            novaSerie = trExService.novaSerie(jwtToken, serie);
        }
        catch (JWTVerificationException jve) {
            return ResponseEntity.status(401).build();
        }
        catch (AuthorizationException ae) {
            return ResponseEntity.status(403).build();
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).build();
        }
        catch (InvalidFieldException ife) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(201).body(novaSerie);
    }

    @PatchMapping("/edit")
    public ResponseEntity<?> editarSerie(@RequestHeader("Authorization") String jwtToken,
                                         @RequestBody TreinoExercicioDTO serie)
    {
        TreinoExercicioDTO serieEditada;
        try {
            serieEditada = trExService.editar(jwtToken, serie);
        }
        catch (JWTVerificationException jve) {
            return ResponseEntity.status(401).build();
        }
        catch (InvalidFieldException ife) {
            return ResponseEntity.badRequest().build();
        }
        catch (AuthorizationException ae) {
            return ResponseEntity.status(403).build();
        }
        catch (EntityNotFoundException e)
        {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.status(200).body(serieEditada);
    }
}
