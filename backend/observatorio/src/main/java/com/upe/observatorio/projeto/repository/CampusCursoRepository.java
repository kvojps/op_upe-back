package com.upe.observatorio.projeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.projeto.domain.Campus;
import com.upe.observatorio.projeto.domain.CampusCurso;
import com.upe.observatorio.projeto.domain.Curso;

public interface CampusCursoRepository extends JpaRepository<CampusCurso, Long>{
	Optional<CampusCurso> findByCampusAndCurso(Campus campus, Curso curso);
}
