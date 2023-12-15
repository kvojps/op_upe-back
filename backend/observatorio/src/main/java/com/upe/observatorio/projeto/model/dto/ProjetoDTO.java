package com.upe.observatorio.projeto.model.dto;

import java.util.Date;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.upe.observatorio.projeto.model.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.model.enums.ModalidadeEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProjetoDTO {
	
	@Schema(example = "PESQUISA", description = "Área temática de um projeto")
	@Enumerated(EnumType.STRING)
	@NotNull
	private AreaTematicaEnum areaTematica;
	
	@Schema(example = "OFICINA", description = "Modalidade de um projeto")
	@Enumerated(EnumType.STRING)
	@NotNull
	private ModalidadeEnum modalidade;
	
	@Schema(example = "Observatório de projetos de pesquisa, extensão e inovação da Universidade de Pernambuco", 
			description = "Título de um projeto")
	@NotBlank
	private String titulo;
	
	@Schema(example = "O OPUPE tem o intuito de concentrar informações sobre os projetos da UPE...", 
			description = "Resumo do projeto")
	private String resumo;
	
	@Schema(example = "A transparência da informação...", description = "Introducão do projeto")
	@NotBlank
	private String introducao;
	
	@Schema(example = "Foi necessário uma pesquisa bibliográfica...", description = "Fundamentação do projeto")
	private String fundamentacao;
	
	@Schema(example = "Compartilhar as experiências obtidas nos projetos...", description = "Objetivos do projeto")
	@NotBlank
	private String objetivos;
	
	@Schema(example = "Diante disso, é possível evidenciar...", description = "Conclusão do projeto")
	@NotBlank
	private String conclusao;
	
	@Schema(example = "https://www.youtube.com/", description = "Memória visual do projeto")
	private String memoriaVisual;
	
	@Schema(example = "2020-04-22", description = "Data de início do projeto")
	private Date dataInicio;
	
	@Schema(example = "2020-04-23", description = "Data de finalização do projeto")
	private Date dataFim;
	
	@Schema(example = "Estudantes da UPE", description = "Público alvo do projeto")
	private String publicoAlvo;
	
	@Schema(example = "1000", description = "Pessoas atendidas pelo projeto")
	private int pessoasAtendidas;
	
	@Schema(example = "S/N + descrição caso tenha", description = "Suporte financeiro do projeto")
	private String suporteFinanceiro;
	
	@Schema(example = "true", description = "Visibilidade de um projeto")
	@NotNull
	private Boolean visibilidade;
	
	private Long usuarioId;
	
	private Long campusId;
}
