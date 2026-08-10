package com.example.meuTreino.security;

import com.example.meuTreino.model.entidade.Usuario;
import com.example.meuTreino.repository.UsuarioRepository;
import com.example.meuTreino.security.userDetails.UserDetailsImpl;
import com.example.meuTreino.service.JwtTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private UsuarioRepository userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException
    {
        if (endpointIsAuthenticated(request)) {
            String token = recoverToken(request);
            if (token==null) {
                throw new RuntimeException("token ausente");
            }
            String subject = jwtTokenService.getSubjectFromToken(token);
            Usuario usuario = userRepo.findByEmail(subject)
                    .orElseThrow(() -> new RuntimeException("usuario nao existe"));
            UserDetailsImpl userDetails = new UserDetailsImpl(usuario);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails.getUsername(),
                            null,
                            userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        return authorizationHeader==null
                ? null
                : authorizationHeader.substring(7);
    }

    private boolean endpointIsAuthenticated(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return !Arrays.asList(
                SecurityConfiguration.PUBLIC_ENDPOINTS).contains(requestURI);
    }
}
