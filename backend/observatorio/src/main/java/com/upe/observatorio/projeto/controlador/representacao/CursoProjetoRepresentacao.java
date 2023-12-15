package com.upe.observatorio.projeto.controlador.representacao;

import com.upe.observatorio.projeto.model.CursoProjeto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CursoProjetoRepresentacao {

	@Schema(example = "1", description = "Id referente ao cursoProjeto")
	private Long id;

	public CursoProjetoRepresentacao(CursoProjeto cursoProjeto) {
		this.id = cursoProjeto.getId();
	}
}
