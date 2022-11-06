package com.upe.observatorio.projeto.controller.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ProjetoRepresentation implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String areaTematica;
	
	private String modalidade;
	
	private String titulo;
	
	private String resumo;
	
	private String introducao;
	
	private String fundamentacao;
	
	private String objetivos;
	
	private String memoriaVisual;
	
	private Date dataInicio;
	
	private Date dataFim;
	
	private String publicoAlvo;
	
	private int pessoasAtendidas;
	
	private Double suporteFinanceiro;
	
	private List<CursoProjetoRepresentation> cursoProjetos;
}
