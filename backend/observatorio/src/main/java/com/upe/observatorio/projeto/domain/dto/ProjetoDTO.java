package com.upe.observatorio.projeto.domain.dto;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.upe.observatorio.projeto.domain.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.domain.enums.ModalidadeEnum;

import lombok.Data;

@Data
public class ProjetoDTO {
	
	@Enumerated(EnumType.STRING)
	private AreaTematicaEnum areaTematica;
	
	@Enumerated(EnumType.STRING)
	private ModalidadeEnum modalidade;
	
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
	
	@NotBlank
	private String conclusao;
	
	private String memoriaVisual;
	
	@NotNull
	private Date dataInicio;
	
	private Date dataFim;
	
	private String publicoAlvo;
	
	private int pessoasAtendidas;
	
	private Double suporteFinanceiro;
	
}
