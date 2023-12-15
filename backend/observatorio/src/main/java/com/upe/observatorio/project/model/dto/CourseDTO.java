package com.upe.observatorio.project.model.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;

import com.upe.observatorio.project.model.enums.CourseTypeEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CourseDTO {
	
	@Schema(example = "Engenharia de Software", description = "Nome do curso")
	@NotBlank
	private String nome;
	
	@Schema(example = "BACHARELADO", description = "Tipo do curso")
	@Enumerated(EnumType.STRING)
	private CourseTypeEnum tipo;
	
}
