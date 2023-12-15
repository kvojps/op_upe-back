package com.upe.observatorio.project.controller.response;

import com.upe.observatorio.project.model.Campus;
import com.upe.observatorio.project.model.CampusCurso;
import com.upe.observatorio.project.model.Projeto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class CampusResponse {

	@Schema(example = "1", description = "Id referente ao campus")
	private Long id;

	@Schema(example = "UPE Multicampi - Garanhuns", description = "Nome do campus da universidade" )
	private String nome;

	@Schema(example = "Garanhuns", description = "Cidade onde o campus está localizado")
	private String cidade;

	@Schema(example = "São José", description = "Bairro onde o campus está localizado")
	private String bairro;

	@Schema(example = "Capitão Pedro Rodrigues", description = "Rua onde o campus está localizado")
	private String rua;

	private List<CampusCourseResponse> campusCurso;
	
	private List<ProjectResponse> projetos;

	public CampusResponse(Campus campus) {
		this.id = campus.getId();
		this.nome = campus.getNome();
		this.cidade = campus.getCidade();
		this.bairro = campus.getBairro();
		this.rua = campus.getRua();
		this.campusCurso = converterCampusCursos(campus.getCampusCurso());
		this.projetos = converterProjetos(campus.getProjetos());
	}

	private List<CampusCourseResponse> converterCampusCursos(List<CampusCurso> campusCursos) {
		return campusCursos.stream().map(CampusCourseResponse::new).toList();
	}

	private List<ProjectResponse> converterProjetos(List<Projeto> projetos) {
		return projetos.stream().map(ProjectResponse::new).toList();
	}
}
