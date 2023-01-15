package com.upe.observatorio.publicacao.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.upe.observatorio.projeto.domain.Projeto;
import com.upe.observatorio.usuario.domain.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Publicacao {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Integer curtidas;
	
	private Integer descurtidas;
	
	@OneToOne
	private Projeto projeto;
	
	@ManyToOne
	private Usuario usuario;
}
