package com.upe.observatorio.projeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.projeto.model.Campus;
import com.upe.observatorio.projeto.model.CampusCurso;
import com.upe.observatorio.projeto.model.Curso;

public interface CampusCourseRepository extends JpaRepository<CampusCurso, Long>{
	Optional<CampusCurso> findByCampusAndCurso(Campus campus, Curso curso);
}
