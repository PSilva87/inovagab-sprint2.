package com.inovagab.inovagab.service;

import com.inovagab.inovagab.config.security.UsuarioUserDetails;
import com.inovagab.inovagab.dto.LoginRequest;
import com.inovagab.inovagab.dto.LoginResponse;
import com.inovagab.inovagab.model.Role;
import com.inovagab.inovagab.model.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock private AuthenticationManager authenticationManager;
    @Mock private TokenService tokenService;
    @Mock private Authentication authentication;

    @Test
    void deveRetornarTokenNoLoginValido() {
        Usuario usuario = new Usuario("Líder", "lider@inovagab.com", "senha", Role.LIDER);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(new UsuarioUserDetails(usuario));
        when(tokenService.gerarToken("lider@inovagab.com", "LIDER")).thenReturn("token-jwt");

        LoginRequest request = new LoginRequest();
        request.setEmail("lider@inovag.com");
        request.setEmail("lider@inovagab.com");
        request.setSenha("123456");

        LoginResponse resposta = new AuthService(authenticationManager, tokenService).login(request);

        assertNotNull(resposta.getToken());
        assertEquals("token-jwt", resposta.getToken());
        assertEquals("LIDER", resposta.getUsuario().getRole().name());
    }
}
