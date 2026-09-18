package com.inovagab.inovagab.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;

@Document(collection = "ideias")
public class Ideia {
    @Id private String id;
    private String titulo;
    private String descricao;
    private StatusIdeia status = StatusIdeia.PENDENTE;
    private Prioridade prioridade = Prioridade.MEDIA;
    private Integer pontuacao;
    private String usuarioId;
    private String estrategiaId;
    private Instant dataCriacao = Instant.now();
    private Instant dataAtualizacao = Instant.now();
    public Ideia() { }
    public Ideia(String titulo, String descricao, String usuarioId, String estrategiaId) { this.titulo=titulo; this.descricao=descricao; this.usuarioId=usuarioId; this.estrategiaId=estrategiaId; }
    public String getId(){return id;} public String getTitulo(){return titulo;} public String getDescricao(){return descricao;}
    public StatusIdeia getStatus(){return status;} public Prioridade getPrioridade(){return prioridade;} public Integer getPontuacao(){return pontuacao;}
    public String getUsuarioId(){return usuarioId;} public String getEstrategiaId(){return estrategiaId;} public Instant getDataCriacao(){return dataCriacao;} public Instant getDataAtualizacao(){return dataAtualizacao;}
    public void atualizar(String titulo,String descricao,String estrategiaId){this.titulo=titulo;this.descricao=descricao;this.estrategiaId=estrategiaId;this.dataAtualizacao=Instant.now();}
    public void priorizar(Prioridade prioridade,Integer pontuacao){this.prioridade=prioridade;this.pontuacao=pontuacao;this.status=StatusIdeia.PRIORIZADA;this.dataAtualizacao=Instant.now();}
    public void aprovar(boolean aprovada){this.status=aprovada?StatusIdeia.APROVADA:StatusIdeia.REJEITADA;this.dataAtualizacao=Instant.now();}
}
