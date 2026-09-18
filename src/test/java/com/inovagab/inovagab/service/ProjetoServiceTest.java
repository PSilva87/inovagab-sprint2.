package com.inovagab.inovagab.service;

import com.inovagab.inovagab.dto.ProgressoRequest;
import com.inovagab.inovagab.dto.ProjetoRequest;
import com.inovagab.inovagab.dto.ResultadoRequest;
import com.inovagab.inovagab.model.Projeto;
import com.inovagab.inovagab.repository.ProjetoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProjetoServiceTest {

    @Mock private ProjetoRepository repository;
    private ProjetoService service;

    @BeforeEach
    void preparar() {
        service = new ProjetoService(repository);
        when(repository.save(any(Projeto.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void deveCriarProjetoPlanejado() {
        Projeto projeto = service.criar(request());

        assertEquals("PLANEJADO", projeto.getStatus());
        assertEquals(1000, projeto.getInvestimento());
    }

    @Test
    void deveAtualizarProgressoEResultado() {
        Projeto projeto = new Projeto("Portal", "Descrição", "estrategia-1", 1000, LocalDate.of(2026, 12, 31));
        when(repository.findById("projeto-1")).thenReturn(Optional.of(projeto));

        ProgressoRequest progresso = new ProgressoRequest();
        progresso.setProgresso(50);
        ResultadoRequest resultado = new ResultadoRequest();
        resultado.setResultados("Economia de tempo");
        resultado.setRetornoFinanceiro(1500);

        service.progresso("projeto-1", progresso);
        Projeto atualizado = service.resultados("projeto-1", resultado);

        assertEquals(50, atualizado.getProgresso());
        assertEquals("EM_ANDAMENTO", atualizado.getStatus());
        assertEquals(1500, atualizado.getRetornoFinanceiro());
    }

    private ProjetoRequest request() {
        ProjetoRequest request = new ProjetoRequest();
        request.setNome("Portal");
        request.setDescricao("Descrição");
        request.setEstrategiaId("estrategia-1");
        request.setInvestimento(1000);
        request.setPrazo(LocalDate.of(2026, 12, 31));
        return request;
    }
}
