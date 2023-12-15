package com.upe.observatorio.project.controller.response;

import com.upe.observatorio.project.model.CampusCurso;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CampusCourseResponse {

	@Schema(example = "1", description = "Id referente ao campusCurso")
	private Long id;

	public CampusCourseResponse(CampusCurso campusCurso) {
		this.id = campusCurso.getId();
	}
}
