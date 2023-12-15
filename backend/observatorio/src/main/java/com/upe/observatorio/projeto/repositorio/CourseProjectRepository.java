package com.upe.observatorio.projeto.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.projeto.dominio.Curso;
import com.upe.observatorio.projeto.dominio.CursoProjeto;
import com.upe.observatorio.projeto.dominio.Projeto;

public interface CourseProjectRepository extends JpaRepository<CursoProjeto, Long>{
	Optional<CursoProjeto> findByCursoAndProjeto(Curso curso, Projeto projeto);
}
