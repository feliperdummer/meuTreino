package com.example.meuTreino.controller;

import com.example.meuTreino.model.dto.LoginUsuarioDTO;
import com.example.meuTreino.model.entidade.Usuario;
import com.example.meuTreino.model.dto.UsuarioDTO;
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
        String jwtToken = authService.login(usuario.email(), usuario.senha());
        if (jwtToken==null) {
            return ResponseEntity.badRequest().body("email ou senha inválidos");
        }
        return ResponseEntity.status(200).body(jwtToken);
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastro(@RequestBody Usuario usuario) {
        Usuario novoUsuario = authService.cadastro(usuario);
        if (novoUsuario==null) {
            return ResponseEntity.badRequest().body("Dados inválidos");
        }
        return ResponseEntity.status(201).body(new UsuarioDTO(novoUsuario));
    }
}
