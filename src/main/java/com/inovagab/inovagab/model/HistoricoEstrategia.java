package com.inovagab.inovagab.model;

import java.time.Instant;
import java.util.UUID;

public class HistoricoEstrategia {
    private String id;
    private Instant data;
    private String categoria;
    private String campanha;

    public HistoricoEstrategia() { }
    public HistoricoEstrategia(Instant data, String categoria, String campanha) {
        this.id = UUID.randomUUID().toString();
        this.data = data;
        this.categoria = categoria;
        this.campanha = campanha;
    }
    public String getId() { return id; }
    public Instant getData() { return data; }
    public String getCategoria() { return categoria; }
    public String getCampanha() { return campanha; }
}
