package com.example.meuTreino.controller;

import com.example.meuTreino.model.Usuario;
import com.example.meuTreino.model.dto.UsuarioDTO;
import com.example.meuTreino.service.AuthService;
import org.apache.coyote.Response;
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
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        return null;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastro(@RequestBody Usuario usuario) {
        Usuario novoUsuario = authService.cadastro(usuario);
        if (novoUsuario==null) {
            return ResponseEntity.badRequest().body("Dados inválidos");
        }
        return ResponseEntity.status(201).body(new UsuarioDTO(novoUsuario));
    }

    @PutMapping("/editar")
    public ResponseEntity<?> editar(@RequestBody Usuario usuario) {
        return null;
    }
}
