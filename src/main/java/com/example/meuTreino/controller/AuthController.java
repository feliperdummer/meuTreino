package com.example.meuTreino.controller;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.meuTreino.model.dto.LoginUsuarioDTO;
import com.example.meuTreino.model.entidade.Usuario;
import com.example.meuTreino.model.dto.UsuarioDTO;
import com.example.meuTreino.model.exception.ExistingUserException;
import com.example.meuTreino.model.exception.InvalidCredentialsException;
import com.example.meuTreino.model.exception.InvalidFieldException;
import com.example.meuTreino.model.exception.UserNotFoundException;
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
        catch (InvalidCredentialsException | UserNotFoundException e) {
            return ResponseEntity.badRequest().body("Credenciais inválidas");
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
        catch (InvalidFieldException ife) {
            return ResponseEntity.badRequest().body("Campo(s) inválidos");
        }
        catch (ExistingUserException eue) {
            return ResponseEntity.badRequest().body("Email já cadastrado");
        }
        return ResponseEntity.status(201).body(new UsuarioDTO(novoUsuario));
    }
}
