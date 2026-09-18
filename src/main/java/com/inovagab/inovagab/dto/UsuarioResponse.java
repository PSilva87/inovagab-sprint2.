package com.inovagab.inovagab.dto;

import com.inovagab.inovagab.model.Role;

public class UsuarioResponse {

    private String id;
    private String nome;
    private String email;
    private Role role;

    public UsuarioResponse(String id, String nome, String email, Role role) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}