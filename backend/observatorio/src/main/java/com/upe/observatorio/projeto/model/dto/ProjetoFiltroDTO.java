package com.upe.observatorio.projeto.model.dto;

import com.upe.observatorio.projeto.model.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.model.enums.ModalidadeEnum;
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
