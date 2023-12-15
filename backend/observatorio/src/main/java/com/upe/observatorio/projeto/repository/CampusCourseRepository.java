package com.upe.observatorio.projeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.projeto.dominio.Campus;
import com.upe.observatorio.projeto.dominio.CampusCurso;
import com.upe.observatorio.projeto.dominio.Curso;

public interface CampusCourseRepository extends JpaRepository<CampusCurso, Long>{
	Optional<CampusCurso> findByCampusAndCurso(Campus campus, Curso curso);
}
