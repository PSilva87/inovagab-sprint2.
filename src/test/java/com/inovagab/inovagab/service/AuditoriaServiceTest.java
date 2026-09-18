package com.inovagab.inovagab.service;

import com.inovagab.inovagab.dto.MetricasResponse;
import com.inovagab.inovagab.model.RegistroAuditoria;
import com.inovagab.inovagab.repository.RegistroAuditoriaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuditoriaServiceTest {

    @Mock private RegistroAuditoriaRepository repository;

    @Test
    void deveRegistrarAcaoECalcularMetricas() {
        AuditoriaService service = new AuditoriaService(repository);
        RegistroAuditoria estrategia = new RegistroAuditoria("lider@inovagab.com", "CRIAR", "ESTRATEGIA", "1", "Estratégia");
        RegistroAuditoria projeto = new RegistroAuditoria("gestor@inovagab.com", "CRIAR", "PROJETO", "2", "Projeto");
        when(repository.findAll()).thenReturn(List.of(estrategia, projeto));

        service.registrar("lider@inovagab.com", "CRIAR", "ESTRATEGIA", "1", "Estratégia");
        MetricasResponse metricas = service.metricas();

        verify(repository).save(org.mockito.ArgumentMatchers.any(RegistroAuditoria.class));
        assertEquals(2, metricas.totalRegistrosAuditoria());
        assertEquals(2, metricas.acoesUltimas24Horas());
        assertEquals(1L, metricas.acoesPorEntidade().get("ESTRATEGIA"));
        assertEquals(1L, metricas.acoesPorEntidade().get("PROJETO"));
    }
}
