package com.upe.observatorio.usuario.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.upe.observatorio.projeto.domain.Projeto;
import com.upe.observatorio.publicacao.domain.Publicacao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nome; 
	
	private String email;
	
	private String senha;
	
	private String matricula;
	
	@OneToMany(mappedBy = "usuario")
	private List<Projeto> projetos;
	
	@OneToMany(mappedBy = "usuario")
	private List<Publicacao> publicacoes;
	
	@ManyToMany
	private List<Perfil> perfis;
	
}
