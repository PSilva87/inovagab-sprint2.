package com.inovagab.inovagab.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "estrategias")
public class Estrategia {

    @Id
    private String id;

    private String titulo;
    private String descricao;
    private String categoria;
    private String campanha;
    private boolean ativa;
    private Instant dataCriacao;
    private Instant dataAtualizacao;
    private List<HistoricoEstrategia> historico = new ArrayList<>();

    public Estrategia() {
    }

    public Estrategia(
            String titulo,
            String descricao,
            String categoria,
            String campanha,
            boolean ativa) {

        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.campanha = campanha;
        this.ativa = ativa;
        this.dataCriacao = Instant.now();
        this.dataAtualizacao = this.dataCriacao;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCampanha() {
        return campanha;
    }

    public void setCampanha(String campanha) {
        this.campanha = campanha;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public Instant getDataCriacao() { return dataCriacao; }
    public Instant getDataAtualizacao() { return dataAtualizacao; }
    public List<HistoricoEstrategia> getHistorico() { return historico; }

    public void atualizar(String titulo, String descricao, String categoria, String campanha, boolean ativa) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.campanha = campanha;
        this.ativa = ativa;
        this.dataAtualizacao = Instant.now();
        this.historico.add(new HistoricoEstrategia(this.dataAtualizacao, categoria, campanha));
    }
}
