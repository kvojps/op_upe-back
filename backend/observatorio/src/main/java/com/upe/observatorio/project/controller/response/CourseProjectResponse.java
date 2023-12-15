package com.upe.observatorio.project.controller.response;

import com.upe.observatorio.project.model.CursoProjeto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CourseProjectResponse {

	@Schema(example = "1", description = "Id referente ao cursoProjeto")
	private Long id;

	public CourseProjectResponse(CursoProjeto cursoProjeto) {
		this.id = cursoProjeto.getId();
	}
}
