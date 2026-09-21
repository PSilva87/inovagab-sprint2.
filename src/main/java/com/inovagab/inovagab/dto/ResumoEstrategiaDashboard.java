package com.inovagab.inovagab.dto;

public record ResumoEstrategiaDashboard(
        String estrategiaId,
        String estrategiaTitulo,
        long totalProjetos,
        double investimentoTotal,
        double retornoFinanceiroTotal,
        double lucroTotal,
        double roiPercentual,
        double produtividadeMedia) {
}
