package com.inovagab.inovagab.service;

import com.inovagab.inovagab.dto.MetricasResponse;
import com.inovagab.inovagab.model.RegistroAuditoria;
import com.inovagab.inovagab.repository.RegistroAuditoriaRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuditoriaService {

    private final RegistroAuditoriaRepository repository;

    public AuditoriaService(RegistroAuditoriaRepository repository) {
        this.repository = repository;
    }

    public void registrar(String usuarioEmail, String acao, String entidade, String entidadeId, String detalhes) {
        repository.save(new RegistroAuditoria(usuarioEmail, acao, entidade, entidadeId, detalhes));
    }

    public List<RegistroAuditoria> listar() {
        return repository.findAll().stream()
                .sorted((a, b) -> b.getDataHora().compareTo(a.getDataHora()))
                .toList();
    }

    public MetricasResponse metricas() {
        List<RegistroAuditoria> registros = repository.findAll();
        Instant limite = Instant.now().minus(24, ChronoUnit.HOURS);
        Map<String, Long> porEntidade = registros.stream()
                .collect(Collectors.groupingBy(RegistroAuditoria::getEntidade, Collectors.counting()));
        long ultimas24Horas = registros.stream()
                .filter(registro -> registro.getDataHora().isAfter(limite))
                .count();
        return new MetricasResponse(registros.size(), ultimas24Horas, porEntidade);
    }
}
