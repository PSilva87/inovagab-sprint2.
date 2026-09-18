package com.inovagab.inovagab.service;

import com.inovagab.inovagab.dto.IdeiaRequest;
import com.inovagab.inovagab.dto.PriorizacaoRequest;
import com.inovagab.inovagab.model.Ideia;
import com.inovagab.inovagab.model.Prioridade;
import com.inovagab.inovagab.model.Role;
import com.inovagab.inovagab.model.StatusIdeia;
import com.inovagab.inovagab.model.Usuario;
import com.inovagab.inovagab.repository.IdeiaRepository;
import com.inovagab.inovagab.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IdeiaServiceTest {

    @Mock private IdeiaRepository ideias;
    @Mock private UsuarioRepository usuarios;
    private IdeiaService service;

    @BeforeEach
    void preparar() {
        service = new IdeiaService(ideias, usuarios);
    }

    @Test
    void operadorDeveCriarIdeiaPendente() {
        Usuario operador = new Usuario("Operador", "operador@inovagab.com", "senha", Role.OPERADOR);
        when(usuarios.findByEmail("operador@inovagab.com")).thenReturn(Optional.of(operador));
        when(ideias.save(any(Ideia.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Ideia criada = service.criar(request(), "operador@inovagab.com");

        assertEquals(StatusIdeia.PENDENTE, criada.getStatus());
        assertEquals("estrategia-1", criada.getEstrategiaId());
    }

    @Test
    void gestorDevePriorizarIdeia() {
        Ideia ideia = new Ideia("Ideia", "Descrição", "usuario-1", "estrategia-1");
        when(ideias.findById("ideia-1")).thenReturn(Optional.of(ideia));
        when(ideias.save(any(Ideia.class))).thenAnswer(invocation -> invocation.getArgument(0));
        PriorizacaoRequest prioridade = new PriorizacaoRequest();
        prioridade.setPrioridade(Prioridade.ALTA);
        prioridade.setPontuacao(90);

        Ideia priorizada = service.priorizar("ideia-1", prioridade);

        assertEquals(StatusIdeia.PRIORIZADA, priorizada.getStatus());
        assertEquals(Prioridade.ALTA, priorizada.getPrioridade());
        assertEquals(90, priorizada.getPontuacao());
    }

    @Test
    void operadorNaoPodeAlterarIdeiaDeOutroUsuario() {
        Ideia ideia = new Ideia("Ideia", "Descrição", "dono", "estrategia-1");
        Usuario outroOperador = new Usuario("Outro", "outro@inovagab.com", "senha", Role.OPERADOR);
        when(ideias.findById("ideia-1")).thenReturn(Optional.of(ideia));
        when(usuarios.findByEmail("outro@inovagab.com")).thenReturn(Optional.of(outroOperador));

        assertThrows(IllegalArgumentException.class,
                () -> service.atualizar("ideia-1", request(), "outro@inovagab.com"));
    }

    private IdeiaRequest request() {
        IdeiaRequest request = new IdeiaRequest();
        request.setTitulo("Automação");
        request.setDescricao("Automatizar relatórios");
        request.setEstrategiaId("estrategia-1");
        return request;
    }
}
