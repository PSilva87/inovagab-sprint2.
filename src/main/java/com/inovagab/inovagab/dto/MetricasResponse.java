package com.inovagab.inovagab.dto;

import java.util.Map;

public record MetricasResponse(
        long totalRegistrosAuditoria,
        long acoesUltimas24Horas,
        Map<String, Long> acoesPorEntidade
) {
}
