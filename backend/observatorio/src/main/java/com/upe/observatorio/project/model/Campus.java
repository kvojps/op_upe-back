package com.upe.observatorio.project.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Campus {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String nome;

	private String cidade;
	
	private String bairro;
	
	private String rua;

	@OneToMany(mappedBy = "campus")
	private List<CampusCurso> campusCurso;
	
	@OneToMany(mappedBy = "campus")
	private List<Projeto> projetos;
}
