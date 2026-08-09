package com.example.meuTreino.service;

import com.example.meuTreino.model.Usuario;
import com.example.meuTreino.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UsuarioRepository userRepo;

    public AuthService(UsuarioRepository userRepo) {
        this.userRepo = userRepo;
    }

    public Usuario login(Usuario usuario) {
        Usuario usuarioExistente = userRepo.findByEmail(usuario.getEmail());
        if (usuarioExistente==null) { return null; }
        return new BCryptPasswordEncoder().
                matches(usuario.getSenha(), usuarioExistente.getSenha())
                    ? usuarioExistente : null;
    }

    public Usuario cadastro(Usuario usuario) {
        if (usuario==null ||
            nomeIsInvalid(usuario.getNome()) ||
            emailIsInvalid(usuario.getEmail()) ||
            senhaIsInvalid(usuario.getSenha()))
        {
            return null;
        }
        usuario.setSenha(
                new BCryptPasswordEncoder().
                        encode(usuario.getSenha())
        );
        return userRepo.save(usuario);
    }

    public boolean existePeloEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    public Optional<Usuario> encontrePeloId(Long userId) {
        return userRepo.findById(userId);
    }

    private boolean nomeIsInvalid(String nome) {
        return nome==null || nome.isBlank() ||
            !nome.matches("^[a-zA-Z]{1,50}$");
    }

    private boolean emailIsInvalid(String email) {
        return email==null || email.isBlank() ||
                !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$") ||
                    userRepo.existsByEmail(email);
    }

    /*
    * Minimo:
    *   -  8 caracteres
    *   -  1 letra maiuscula
    *   -  1 numero
    *   -  1 caractere especial
    * */
    private boolean senhaIsInvalid(String senha) {
        return senha==null || senha.isBlank() ||
                !senha.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[$*&@#])[0-9a-zA-Z$*&@#]{8,}$");
    }
}
