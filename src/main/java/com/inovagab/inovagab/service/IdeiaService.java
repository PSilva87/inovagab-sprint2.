package com.inovagab.inovagab.service;
import com.inovagab.inovagab.dto.*;
import com.inovagab.inovagab.model.Ideia;
import com.inovagab.inovagab.repository.IdeiaRepository;
import com.inovagab.inovagab.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
@Service public class IdeiaService {
 private final IdeiaRepository ideias; private final UsuarioRepository usuarios;
 public IdeiaService(IdeiaRepository ideias,UsuarioRepository usuarios){this.ideias=ideias;this.usuarios=usuarios;}
 public Ideia criar(IdeiaRequest r,String email){var u=usuarios.findByEmail(email).orElseThrow(()->new IllegalArgumentException("Usuário não encontrado"));return ideias.save(new Ideia(r.getTitulo(),r.getDescricao(),u.getId(),r.getEstrategiaId()));}
 public List<Ideia> minhas(String email){var u=usuarios.findByEmail(email).orElseThrow(()->new IllegalArgumentException("Usuário não encontrado"));return ideias.findByUsuarioId(u.getId());}
 public List<Ideia> listar(){return ideias.findAll();}
 public Ideia atualizar(String id,IdeiaRequest r,String email){var ideia=buscar(id);validarDono(ideia,email);ideia.atualizar(r.getTitulo(),r.getDescricao(),r.getEstrategiaId());return ideias.save(ideia);}
 public void excluir(String id,String email){var i=buscar(id);validarDono(i,email);ideias.delete(i);}
 public Ideia priorizar(String id,PriorizacaoRequest r){var i=buscar(id);i.priorizar(r.getPrioridade(),r.getPontuacao());return ideias.save(i);}
 public Ideia aprovar(String id,AprovacaoRequest r){var i=buscar(id);i.aprovar(r.isAprovada());return ideias.save(i);}
 private Ideia buscar(String id){return ideias.findById(id).orElseThrow(()->new IllegalArgumentException("Ideia não encontrada"));}
 private void validarDono(Ideia i,String email){var u=usuarios.findByEmail(email).orElseThrow(()->new IllegalArgumentException("Usuário não encontrado"));if(!i.getUsuarioId().equals(u.getId()))throw new IllegalArgumentException("A ideia pertence a outro operador");}
}
