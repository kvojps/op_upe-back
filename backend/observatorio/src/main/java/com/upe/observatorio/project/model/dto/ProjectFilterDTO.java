package com.upe.observatorio.project.model.dto;

import com.upe.observatorio.project.model.enums.ThematicAreaEnum;
import com.upe.observatorio.project.model.enums.ModalityEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectFilterDTO {
	private String titulo;
	
	private ThematicAreaEnum areaTematica;
	
	private ModalityEnum modalidade;
	
	private String dataInicio;
	
	private String dataFim;
	
	private int page;
	
	private int size;
}
