package com.upe.observatorio.user.controller.response;

import com.upe.observatorio.project.controller.response.ProjectResponse;
import com.upe.observatorio.project.model.Projeto;
import com.upe.observatorio.user.dominio.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {

	@Schema(example = "1", description = "Id referente ao usuário")
	private Long id;
	
	@Schema(example = "José", description = "Nome do usuário")
	private String nome; 
	
	@Schema(example = "jose@gmail.com", description = "E-mail de um usuário")
	private String email;
	
	@Schema(example = "20221412", description = "Matrícula de um usuário")
	private String matricula;
	
	private List<ProjectResponse> projetos;
	
	//adicionar perfil

	public UserResponse(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.matricula =usuario.getMatricula();
		this.projetos = converterProjetos(usuario.getProjetos());
	}

	private List<ProjectResponse> converterProjetos(List<Projeto> projetos) {
		return projetos.stream().map(ProjectResponse::new).toList();
	}
}
