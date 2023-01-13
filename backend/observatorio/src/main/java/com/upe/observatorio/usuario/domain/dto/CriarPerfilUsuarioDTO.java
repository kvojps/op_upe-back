package com.upe.observatorio.usuario.domain.dto;

import java.util.List;

import lombok.Data;

@Data
public class CriarPerfilUsuarioDTO {
	private Long idUsuario;
	
	private List<Long> idPerfis;
}
