package com.inovagab.inovagab.controller;
import com.inovagab.inovagab.dto.*; import com.inovagab.inovagab.model.Ideia; import com.inovagab.inovagab.service.AuditoriaService; import com.inovagab.inovagab.service.IdeiaService;
import jakarta.validation.Valid; import org.springframework.http.*; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/api/ideias") public class IdeiaController {
 private final IdeiaService service; private final AuditoriaService auditoria; public IdeiaController(IdeiaService service,AuditoriaService auditoria){this.service=service;this.auditoria=auditoria;}
 @PostMapping public ResponseEntity<Ideia> criar(@Valid @RequestBody IdeiaRequest r,Authentication a){Ideia ideia=service.criar(r,a.getName());auditoria.registrar(a.getName(),"CRIAR","IDEIA",ideia.getId(),ideia.getTitulo());return ResponseEntity.status(HttpStatus.CREATED).body(ideia);}
 @GetMapping("/minhas") public List<Ideia> minhas(Authentication a){return service.minhas(a.getName());}
 @GetMapping public List<Ideia> listar(){return service.listar();}
 @PutMapping("/{id}") public Ideia atualizar(@PathVariable String id,@Valid @RequestBody IdeiaRequest r,Authentication a){Ideia ideia=service.atualizar(id,r,a.getName());auditoria.registrar(a.getName(),"ATUALIZAR","IDEIA",id,ideia.getTitulo());return ideia;}
 @DeleteMapping("/{id}") public ResponseEntity<Void> excluir(@PathVariable String id,Authentication a){service.excluir(id,a.getName());auditoria.registrar(a.getName(),"EXCLUIR","IDEIA",id,"Ideia removida");return ResponseEntity.noContent().build();}
 @PatchMapping("/{id}/priorizacao") public Ideia priorizar(@PathVariable String id,@Valid @RequestBody PriorizacaoRequest r,Authentication a){Ideia ideia=service.priorizar(id,r);auditoria.registrar(a.getName(),"PRIORIZAR","IDEIA",id,ideia.getPrioridade().name());return ideia;}
 @PatchMapping("/{id}/aprovacao") public Ideia aprovar(@PathVariable String id,@RequestBody AprovacaoRequest r,Authentication a){Ideia ideia=service.aprovar(id,r);auditoria.registrar(a.getName(),r.isAprovada()?"APROVAR":"REJEITAR","IDEIA",id,ideia.getTitulo());return ideia;}
}
