package com.upe.observatorio.project.model.dto;

import com.upe.observatorio.project.model.enums.AreaTematicaEnum;
import com.upe.observatorio.project.model.enums.ModalidadeEnum;
import lombok.Builder;
import lombok.Data;

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
