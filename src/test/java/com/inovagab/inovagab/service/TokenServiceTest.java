package com.inovagab.inovagab.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TokenServiceTest {

    private final TokenService tokenService = new TokenService(
            "utmWR93PDmf64aw1UVnGxwqJvCxEPkU4OhYzrsXFZzo=", 60_000);

    @Test
    void deveGerarEValidarTokenDoUsuario() {
        String token = tokenService.gerarToken("lider@inovagab.com", "LIDER");

        assertNotNull(token);
        assertEquals("lider@inovagab.com", tokenService.validarToken(token));
    }
}
