package com.upe.observatorio.projeto.dominio;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "campus_curso")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CampusCurso {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_campus")
	private Campus campus;

	@ManyToOne
	@JoinColumn(name = "id_curso")
	private Curso curso;
}
