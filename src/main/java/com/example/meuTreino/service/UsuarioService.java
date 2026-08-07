package com.example.meuTreino.service;

import com.example.meuTreino.model.Usuario;
import com.example.meuTreino.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    private final UsuarioRepository userRepo;

    @Autowired
    public UsuarioService(UsuarioRepository userRepo) {
        this.userRepo = userRepo;
    }

    public Usuario salvar(Usuario usuario) {
        if (checkForErrors(usuario)) {
            return null;
        }
        return userRepo.save(usuario);
    }

    public Usuario editar(Usuario usuario) {
        if (checkForErrors(usuario)) {
            return null;
        }
        return userRepo.save(usuario);
    }

    public boolean excluir(Usuario usuario) {
        userRepo.delete(usuario);
        return true;
    }

    public boolean existePeloId(Long userId) {
        return userRepo.existsById(userId);
    }

    public Optional<Usuario> encontrePeloId(Long userId) {
        return userRepo.findById(userId);
    }

    private boolean checkForErrors(Usuario usuario) {
        return (usuario.getNome()==null || usuario.getEmail()==null
            || usuario.getSenha()==null || usuario.getDataNasc()==null);
    }
}
