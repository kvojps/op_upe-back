package com.upe.observatorio.project.model.dto;

import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CampusCourseDTO {
	
	@Schema(example = "1", description = "Id do campus")
	@NotNull
	private Long campusId;
	
	@Schema(example = "2", description = "Id do curso")
	@NotNull
	private Long cursoId;
}
