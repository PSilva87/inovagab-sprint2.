package com.inovagab.inovagab.service;

import com.inovagab.inovagab.dto.DashboardResponse;
import com.inovagab.inovagab.dto.ResumoEstrategiaDashboard;
import com.inovagab.inovagab.model.Projeto;
import com.inovagab.inovagab.model.StatusIdeia;
import com.inovagab.inovagab.repository.EstrategiaRepository;
import com.inovagab.inovagab.repository.IdeiaRepository;
import com.inovagab.inovagab.repository.ProjetoRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardService {
    private final EstrategiaRepository estrategias;
    private final IdeiaRepository ideias;
    private final ProjetoRepository projetos;

    public DashboardService(EstrategiaRepository estrategias, IdeiaRepository ideias, ProjetoRepository projetos) {
        this.estrategias = estrategias;
        this.ideias = ideias;
        this.projetos = projetos;
    }

    public DashboardResponse resumo() {
        var listaIdeias = ideias.findAll();
        var listaProjetos = projetos.findAll();
        double investimentoTotal = listaProjetos.stream().mapToDouble(Projeto::getInvestimento).sum();
        double retornoTotal = listaProjetos.stream().mapToDouble(Projeto::getRetornoFinanceiro).sum();
        double lucroTotal = retornoTotal - investimentoTotal;

        Map<String, String> titulosPorEstrategia = estrategias.findAll().stream()
                .collect(Collectors.toMap(e -> e.getId(), e -> e.getTitulo(), (primeiro, segundo) -> primeiro));

        Map<String, ResumoEstrategiaDashboard> resultadosPorEstrategia = listaProjetos.stream()
                .collect(Collectors.groupingBy(Projeto::getEstrategiaId, LinkedHashMap::new, Collectors.toList()))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> resumoEstrategia(
                        entry.getKey(), titulosPorEstrategia.getOrDefault(entry.getKey(), "Estratégia removida"), entry.getValue()),
                        (primeiro, segundo) -> primeiro, LinkedHashMap::new));

        return new DashboardResponse(
                estrategias.count(),
                listaIdeias.size(),
                listaIdeias.stream().filter(ideia -> ideia.getStatus() == StatusIdeia.PENDENTE).count(),
                listaIdeias.stream().filter(ideia -> ideia.getStatus() == StatusIdeia.APROVADA).count(),
                listaProjetos.size(),
                investimentoTotal,
                retornoTotal,
                lucroTotal,
                calcularRoi(lucroTotal, investimentoTotal),
                listaProjetos.stream().collect(Collectors.groupingBy(Projeto::getStatus, Collectors.counting())),
                resultadosPorEstrategia);
    }

    private ResumoEstrategiaDashboard resumoEstrategia(String estrategiaId, String titulo, List<Projeto> projetosDaEstrategia) {
        double investimento = projetosDaEstrategia.stream().mapToDouble(Projeto::getInvestimento).sum();
        double retorno = projetosDaEstrategia.stream().mapToDouble(Projeto::getRetornoFinanceiro).sum();
        double lucro = retorno - investimento;
        return new ResumoEstrategiaDashboard(
                estrategiaId, titulo, projetosDaEstrategia.size(), investimento, retorno, lucro, calcularRoi(lucro, investimento));
    }

    private double calcularRoi(double lucro, double investimento) {
        return investimento == 0 ? 0 : Math.round((lucro / investimento) * 10000.0) / 100.0;
    }
}
