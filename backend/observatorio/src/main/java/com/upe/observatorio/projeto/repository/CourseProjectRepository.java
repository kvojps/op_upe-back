package com.upe.observatorio.projeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.projeto.model.Curso;
import com.upe.observatorio.projeto.model.CursoProjeto;
import com.upe.observatorio.projeto.model.Projeto;

public interface CourseProjectRepository extends JpaRepository<CursoProjeto, Long>{
	Optional<CursoProjeto> findByCursoAndProjeto(Curso curso, Projeto projeto);
}
