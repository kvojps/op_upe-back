package com.upe.observatorio.projeto.controlador.modelo;

import java.io.Serializable;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CursoRepresentacao implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Schema(example = "1", description = "Id referente ao curso")
	private Long id;
	
	@Schema(example = "Engenharia de Software", description = "Nome do curso")
	private String nome;
	
	private List<CampusCursoRepresentacao> campusCurso;
	
	private List<CursoProjetoRepresentacao> cursoProjeto;
}
