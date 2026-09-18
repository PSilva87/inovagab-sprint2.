package com.inovagab.inovagab.controller;

import com.inovagab.inovagab.dto.MetricasResponse;
import com.inovagab.inovagab.model.RegistroAuditoria;
import com.inovagab.inovagab.service.AuditoriaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;

    public AuditoriaController(AuditoriaService auditoriaService) {
        this.auditoriaService = auditoriaService;
    }

    @GetMapping("/auditoria")
    public List<RegistroAuditoria> listar() {
        return auditoriaService.listar();
    }

    @GetMapping("/metricas")
    public MetricasResponse metricas() {
        return auditoriaService.metricas();
    }
}
