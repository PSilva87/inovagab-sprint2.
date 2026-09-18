package com.inovagab.inovagab.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("auditorias")
public class RegistroAuditoria {

    @Id
    private String id;
    private String usuarioEmail;
    private String acao;
    private String entidade;
    private String entidadeId;
    private String detalhes;
    private Instant dataHora;

    public RegistroAuditoria() {
    }

    public RegistroAuditoria(String usuarioEmail, String acao, String entidade, String entidadeId, String detalhes) {
        this.usuarioEmail = usuarioEmail;
        this.acao = acao;
        this.entidade = entidade;
        this.entidadeId = entidadeId;
        this.detalhes = detalhes;
        this.dataHora = Instant.now();
    }

    public String getId() { return id; }
    public String getUsuarioEmail() { return usuarioEmail; }
    public String getAcao() { return acao; }
    public String getEntidade() { return entidade; }
    public String getEntidadeId() { return entidadeId; }
    public String getDetalhes() { return detalhes; }
    public Instant getDataHora() { return dataHora; }
}
