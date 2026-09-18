package com.inovagab.inovagab.dto;

import jakarta.validation.constraints.NotBlank;

public class EstrategiaRequest {

    @NotBlank
    private String titulo;

    @NotBlank
    private String descricao;

    @NotBlank
    private String categoria;

    @NotBlank
    private String campanha;

    private boolean ativa;

    public EstrategiaRequest() {
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
}