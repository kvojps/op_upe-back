package com.upe.observatorio.projeto.domain.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ProjetoDTO {
	
	@NotBlank
	private String areaTematica;
	
	@NotBlank
	private String modalidade;
	
	@NotBlank
	private String titulo;
	
	@NotBlank
	private String resumo;
	
	@NotBlank
	private String introducao;
	
	@NotBlank
	private String fundamentacao;
	
	@NotBlank
	private String objetivos;
	
	private String memoriaVisual;
	
	@NotBlank
	private Date dataInicio;
	
	private Date dataFim;
	
	private String publicoAlvo;
	
	private int pessoasAtendidas;
	
	private Double suporteFinanceiro;
	
}
