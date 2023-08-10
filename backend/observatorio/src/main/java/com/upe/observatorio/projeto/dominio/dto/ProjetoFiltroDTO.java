package com.upe.observatorio.projeto.dominio.dto;

import com.upe.observatorio.projeto.dominio.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.dominio.enums.ModalidadeEnum;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ProjetoFiltroDTO {
	private String titulo;
	
	private AreaTematicaEnum areaTematica;
	
	private ModalidadeEnum modalidade;
	
	private String dataInicio;
	
	private String dataFim;
	
	private int page;
	
	private int size;
}
