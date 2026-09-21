package com.inovagab.inovagab.dto;
import java.util.Map;
public record DashboardResponse(
        long totalEstrategias,
        long totalIdeias,
        long ideiasPendentes,
        long ideiasAprovadas,
        long totalProjetos,
        double investimentoTotal,
        double retornoFinanceiroTotal,
        double lucroTotal,
        double roiPercentual,
        Map<String, Long> projetosPorStatus,
        Map<String, ResumoEstrategiaDashboard> resultadosPorEstrategia) {
}
