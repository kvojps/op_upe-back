package com.upe.observatorio.usuario.controlador.model;

import com.upe.observatorio.projeto.controlador.representacao.ProjetoRepresentacao;
import com.upe.observatorio.projeto.model.Projeto;
import com.upe.observatorio.usuario.dominio.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class UsuarioRepresentacao {

	@Schema(example = "1", description = "Id referente ao usuário")
	private Long id;
	
	@Schema(example = "José", description = "Nome do usuário")
	private String nome; 
	
	@Schema(example = "jose@gmail.com", description = "E-mail de um usuário")
	private String email;
	
	@Schema(example = "20221412", description = "Matrícula de um usuário")
	private String matricula;
	
	private List<ProjetoRepresentacao> projetos;
	
	//adicionar perfil

	public UsuarioRepresentacao(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.email = usuario.getEmail();
		this.matricula =usuario.getMatricula();
		this.projetos = converterProjetos(usuario.getProjetos());
	}

	private List<ProjetoRepresentacao> converterProjetos(List<Projeto> projetos) {
		return projetos.stream().map(ProjetoRepresentacao::new).toList();
	}
}
