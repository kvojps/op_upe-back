package com.upe.observatorio.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.project.model.Campus;
import com.upe.observatorio.project.model.CampusCurso;
import com.upe.observatorio.project.model.Curso;

public interface CampusCourseRepository extends JpaRepository<CampusCurso, Long>{
	Optional<CampusCurso> findByCampusAndCurso(Campus campus, Curso curso);
}
