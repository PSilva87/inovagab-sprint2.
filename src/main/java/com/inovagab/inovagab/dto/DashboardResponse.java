package com.inovagab.inovagab.dto;
import java.util.Map;
public record DashboardResponse(long totalEstrategias,long totalIdeias,long ideiasPendentes,long ideiasAprovadas,long totalProjetos,double investimentoTotal,double retornoFinanceiroTotal,Map<String,Long> projetosPorStatus) { }
