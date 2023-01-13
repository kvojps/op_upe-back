package com.upe.observatorio.usuario.domain.dto;

import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PerfilDTO {
	
	@Schema(example = "COORDENADOR", description = "Nome referente ao perfil")
	@NotBlank
	private String nome;
}
