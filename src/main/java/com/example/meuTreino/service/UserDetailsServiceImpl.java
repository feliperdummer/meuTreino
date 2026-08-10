package com.example.meuTreino.service;

import com.example.meuTreino.model.entidade.Usuario;
import com.example.meuTreino.repository.UsuarioRepository;
import com.example.meuTreino.security.userDetails.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private  UsuarioRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = userRepo.findByEmail(username).orElseThrow(
                () -> new RuntimeException("Usuario nao encontrado")
        );
        return new UserDetailsImpl(usuario);
    }
}
