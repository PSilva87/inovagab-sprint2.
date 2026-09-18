package com.inovagab.inovagab.controller;

import com.inovagab.inovagab.dto.EstrategiaRequest;
import com.inovagab.inovagab.dto.EstrategiaResponse;
import com.inovagab.inovagab.service.AuditoriaService;
import com.inovagab.inovagab.service.EstrategiaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;

@RestController
@RequestMapping("/api/estrategias")
public class EstrategiaController {

    private final EstrategiaService estrategiaService;
    private final AuditoriaService auditoriaService;

    public EstrategiaController(EstrategiaService estrategiaService, AuditoriaService auditoriaService) {
        this.estrategiaService = estrategiaService;
        this.auditoriaService = auditoriaService;
    }

    @PostMapping
    public ResponseEntity<EstrategiaResponse> criar(
            @Valid @RequestBody EstrategiaRequest request, Authentication authentication) {

        EstrategiaResponse estrategia = estrategiaService.criar(request);
        auditoriaService.registrar(authentication.getName(), "CRIAR", "ESTRATEGIA", estrategia.getId(), estrategia.getTitulo());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(estrategia);
    }

    @GetMapping
    public ResponseEntity<List<EstrategiaResponse>> listar() {

        return ResponseEntity.ok(estrategiaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstrategiaResponse> buscarPorId(
            @PathVariable String id) {

        return ResponseEntity.ok(estrategiaService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstrategiaResponse> atualizar(@PathVariable String id, @Valid @RequestBody EstrategiaRequest request, Authentication authentication) {
        EstrategiaResponse estrategia = estrategiaService.atualizar(id, request);
        auditoriaService.registrar(authentication.getName(), "ATUALIZAR", "ESTRATEGIA", id, estrategia.getTitulo());
        return ResponseEntity.ok(estrategia);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable String id, Authentication authentication) {
        estrategiaService.excluir(id);
        auditoriaService.registrar(authentication.getName(), "EXCLUIR", "ESTRATEGIA", id, "Estratégia removida");
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/historico")
    public ResponseEntity<List<com.inovagab.inovagab.model.HistoricoEstrategia>> historico(@PathVariable String id) {
        return ResponseEntity.ok(estrategiaService.historico(id));
    }
}
