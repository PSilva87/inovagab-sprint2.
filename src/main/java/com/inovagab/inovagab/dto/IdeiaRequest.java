package com.inovagab.inovagab.dto;
import jakarta.validation.constraints.NotBlank;
public class IdeiaRequest { @NotBlank private String titulo; @NotBlank private String descricao; @NotBlank private String estrategiaId;
 public String getTitulo(){return titulo;} public void setTitulo(String v){titulo=v;} public String getDescricao(){return descricao;} public void setDescricao(String v){descricao=v;} public String getEstrategiaId(){return estrategiaId;} public void setEstrategiaId(String v){estrategiaId=v;} }
