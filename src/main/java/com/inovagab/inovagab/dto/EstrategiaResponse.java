package com.inovagab.inovagab.dto;

public class EstrategiaResponse {

    private String id;
    private String titulo;
    private String descricao;
    private String categoria;
    private String campanha;
    private boolean ativa;

    public EstrategiaResponse(
            String id,
            String titulo,
            String descricao,
            String categoria,
            String campanha,
            boolean ativa) {

        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.campanha = campanha;
        this.ativa = ativa;
    }

    public String getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getCampanha() {
        return campanha;
    }

    public boolean isAtiva() {
        return ativa;
    }
}