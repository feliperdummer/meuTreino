package com.example.meuTreino.controller;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.meuTreino.model.dto.LoginUsuarioDTO;
import com.example.meuTreino.model.entidade.Usuario;
import com.example.meuTreino.model.dto.UsuarioDTO;
import com.example.meuTreino.model.exception.*;
import com.example.meuTreino.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUsuarioDTO usuario)
    {
        String jwtToken;
        try {
            jwtToken = authService.login(usuario.email(), usuario.senha());
        }
        catch (InvalidCredentialsException | EntityNotFoundException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
        catch (JWTCreationException jce) {
            return ResponseEntity.status(500).build();
        }
        return ResponseEntity.status(200).body(jwtToken);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastro(@RequestBody Usuario usuario) {
        Usuario novoUsuario;
        try {
            novoUsuario = authService.cadastro(usuario);
        }
        catch (InvalidFieldException | ExistingUserException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
        return ResponseEntity.status(201).body(new UsuarioDTO(novoUsuario));
    }
}
