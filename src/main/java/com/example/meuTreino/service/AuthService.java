package com.example.meuTreino.service;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.meuTreino.model.cargos.NomesCargos;
import com.example.meuTreino.model.entidade.Cargo;
import com.example.meuTreino.model.entidade.Usuario;
import com.example.meuTreino.model.exception.*;
import com.example.meuTreino.repository.UsuarioRepository;
import com.example.meuTreino.security.SecurityConfiguration;
import com.example.meuTreino.security.userDetails.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private  UsuarioRepository userRepo;
    @Autowired
    private SecurityConfiguration securityConfiguration;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtTokenService jwtTokenService;

    public String login(String email, String senha) throws EntityNotFoundException,
                                                           InvalidCredentialsException,
                                                           JWTCreationException
    {
        if (!userRepo.existsByEmail(email)) {
            throw new EntityNotFoundException("usuario nao encontrado");
        }
        UsernamePasswordAuthenticationToken userPassAuthToken =
                new UsernamePasswordAuthenticationToken(email, senha);
        Authentication auth;
        try {
            auth = authManager.authenticate(userPassAuthToken);
        }
        catch (Exception e) {
            throw new InvalidCredentialsException("email e/ou senha incorretos");
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return jwtTokenService.generateToken(userDetails);
    }

    public Usuario cadastro(Usuario usuario) throws ExistingUserException,
                                                    InvalidFieldException
    {
        if (usuario==null ||
            nomeIsInvalid(usuario.getNome()) ||
            emailIsInvalid(usuario.getEmail()) ||
            senhaIsInvalid(usuario.getSenha()))
        {
            throw new InvalidFieldException("campo(s) invalidos");
        }
        if (userRepo.existsByEmail(usuario.getEmail())) {
            throw new ExistingUserException("ja existe uma conta com este email");
        }
        usuario.setSenha(
                securityConfiguration.
                        passwordEncoder().encode(usuario.getSenha())
        );
        usuario.setCargo(
                List.of(new Cargo(null, NomesCargos.CARGO_USER))
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
                !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
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
