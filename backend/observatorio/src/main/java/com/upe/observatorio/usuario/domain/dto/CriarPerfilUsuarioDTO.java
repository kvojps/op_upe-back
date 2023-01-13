package com.upe.observatorio.usuario.domain.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CriarPerfilUsuarioDTO {
	@Schema(example = "1", description = "Id referente ao usuário")
	private Long idUsuario;
	
	@Schema(example = "[1,2]", description = "Id referente aos perfis")
	private List<Long> idPerfis;
}
