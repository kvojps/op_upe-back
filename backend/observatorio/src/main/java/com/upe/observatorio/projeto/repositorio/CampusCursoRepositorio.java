package com.upe.observatorio.projeto.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.projeto.dominio.Campus;
import com.upe.observatorio.projeto.dominio.CampusCurso;
import com.upe.observatorio.projeto.dominio.Curso;

public interface CampusCursoRepositorio extends JpaRepository<CampusCurso, Long>{
	Optional<CampusCurso> findByCampusAndCurso(Campus campus, Curso curso);
}
