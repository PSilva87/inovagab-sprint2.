package com.inovagab.inovagab.dto;
import com.inovagab.inovagab.model.Prioridade;
import jakarta.validation.constraints.NotNull;
public class PriorizacaoRequest { @NotNull private Prioridade prioridade; private Integer pontuacao; public Prioridade getPrioridade(){return prioridade;} public void setPrioridade(Prioridade v){prioridade=v;} public Integer getPontuacao(){return pontuacao;} public void setPontuacao(Integer v){pontuacao=v;} }
