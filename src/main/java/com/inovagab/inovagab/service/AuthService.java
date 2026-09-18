package com.inovagab.inovagab.service;

import com.inovagab.inovagab.config.security.UsuarioUserDetails;
import com.inovagab.inovagab.dto.LoginRequest;
import com.inovagab.inovagab.dto.LoginResponse;
import com.inovagab.inovagab.dto.UsuarioResponse;
import com.inovagab.inovagab.model.Usuario;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthService(
            AuthenticationManager authenticationManager,
            TokenService tokenService) {

        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    public LoginResponse login(LoginRequest request) {

        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getSenha()
                )
        );

        UsuarioUserDetails userDetails =
                (UsuarioUserDetails) authentication.getPrincipal();

        Usuario usuario = userDetails.getUsuario();

        String token = tokenService.gerarToken(
                usuario.getEmail(),
                usuario.getRole().name()
        );

        UsuarioResponse usuarioResponse = new UsuarioResponse(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getRole()
        );

        return new LoginResponse(
                token,
                "Bearer",
                usuarioResponse
        );
    }
}