package com.upe.observatorio.projeto.controller.response;

import com.upe.observatorio.projeto.model.CampusCurso;
import com.upe.observatorio.projeto.model.Curso;
import com.upe.observatorio.projeto.model.CursoProjeto;
import com.upe.observatorio.projeto.model.enums.TipoCursoEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class CourseResponse {

	@Schema(example = "1", description = "Id referente ao curso")
	private Long id;
	
	@Schema(example = "Engenharia de Software", description = "Nome do curso")
	private String nome;
	
	@Schema(example = "BACHARELADO", description = "Tipo do curso")
	private TipoCursoEnum tipo;
	
	private List<CampusCourseResponse> campusCurso;
	
	private List<CourseProjectResponse> cursoProjeto;

	public CourseResponse(Curso curso) {
		this.id = curso.getId();
		this.nome = curso.getNome();
		this.tipo = curso.getTipo();
		this.campusCurso = converterCampusCursos(curso.getCampusCurso());
		this.cursoProjeto = converterCursoProjetos(curso.getCursoProjeto());
	}

	private List<CampusCourseResponse> converterCampusCursos(List<CampusCurso> campusCursos) {
		return campusCursos.stream().map(CampusCourseResponse::new).toList();
	}
	private List<CourseProjectResponse> converterCursoProjetos(List<CursoProjeto> cursoProjetos) {
		return cursoProjetos.stream().map(CourseProjectResponse::new).toList();
	}
}
