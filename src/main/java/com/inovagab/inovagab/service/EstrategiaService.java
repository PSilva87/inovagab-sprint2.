package com.inovagab.inovagab.service;

import com.inovagab.inovagab.dto.EstrategiaRequest;
import com.inovagab.inovagab.dto.EstrategiaResponse;
import com.inovagab.inovagab.model.Estrategia;
import com.inovagab.inovagab.repository.EstrategiaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstrategiaService {

    private final EstrategiaRepository estrategiaRepository;

    public EstrategiaService(EstrategiaRepository estrategiaRepository) {
        this.estrategiaRepository = estrategiaRepository;
    }

    public EstrategiaResponse criar(EstrategiaRequest request) {

        Estrategia estrategia = new Estrategia(
                request.getTitulo(),
                request.getDescricao(),
                request.getCategoria(),
                request.getCampanha(),
                request.isAtiva()
        );

        Estrategia salva = estrategiaRepository.save(estrategia);

        return converterParaResponse(salva);
    }

    public List<EstrategiaResponse> listar() {

        return estrategiaRepository.findAll()
                .stream()
                .map(this::converterParaResponse)
                .toList();
    }

    public EstrategiaResponse buscarPorId(String id) {

        Estrategia estrategia = estrategiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estratégia não encontrada"));

        return converterParaResponse(estrategia);
    }

    public EstrategiaResponse atualizar(String id, EstrategiaRequest request) {
        Estrategia estrategia = estrategiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estratégia não encontrada"));
        estrategia.atualizar(request.getTitulo(), request.getDescricao(), request.getCategoria(), request.getCampanha(), request.isAtiva());
        return converterParaResponse(estrategiaRepository.save(estrategia));
    }

    public void excluir(String id) {
        if (!estrategiaRepository.existsById(id)) throw new IllegalArgumentException("Estratégia não encontrada");
        estrategiaRepository.deleteById(id);
    }

    public List<com.inovagab.inovagab.model.HistoricoEstrategia> historico(String id) {
        return estrategiaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estratégia não encontrada"))
                .getHistorico();
    }

    private EstrategiaResponse converterParaResponse(Estrategia estrategia) {

        return new EstrategiaResponse(
                estrategia.getId(),
                estrategia.getTitulo(),
                estrategia.getDescricao(),
                estrategia.getCategoria(),
                estrategia.getCampanha(),
                estrategia.isAtiva()
        );
    }
}
