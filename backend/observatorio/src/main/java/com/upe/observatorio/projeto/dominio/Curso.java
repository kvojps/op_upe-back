package com.upe.observatorio.projeto.dominio;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.upe.observatorio.projeto.dominio.enums.TipoCursoEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Curso {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nome;
	
	@Enumerated(EnumType.STRING)
	private TipoCursoEnum tipo;

	@OneToMany(mappedBy = "curso")
	private List<CampusCurso> campusCurso;

	@OneToMany(mappedBy = "curso")
	private List<CursoProjeto> cursoProjeto;
}
