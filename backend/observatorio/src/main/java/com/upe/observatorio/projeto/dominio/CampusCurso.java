package com.upe.observatorio.projeto.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
