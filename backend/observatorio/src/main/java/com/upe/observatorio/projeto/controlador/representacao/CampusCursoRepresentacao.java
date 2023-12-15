package com.upe.observatorio.projeto.controlador.representacao;

import com.upe.observatorio.projeto.model.CampusCurso;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CampusCursoRepresentacao {

	@Schema(example = "1", description = "Id referente ao campusCurso")
	private Long id;

	public CampusCursoRepresentacao(CampusCurso campusCurso) {
		this.id = campusCurso.getId();
	}
}
