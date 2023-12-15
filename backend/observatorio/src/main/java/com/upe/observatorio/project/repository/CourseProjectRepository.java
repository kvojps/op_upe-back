package com.upe.observatorio.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.project.model.Curso;
import com.upe.observatorio.project.model.CursoProjeto;
import com.upe.observatorio.project.model.Projeto;

public interface CourseProjectRepository extends JpaRepository<CursoProjeto, Long>{
	Optional<CursoProjeto> findByCursoAndProjeto(Curso curso, Projeto projeto);
}
