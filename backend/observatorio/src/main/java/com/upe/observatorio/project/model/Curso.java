package com.upe.observatorio.project.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import com.upe.observatorio.project.model.enums.TipoCursoEnum;

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
