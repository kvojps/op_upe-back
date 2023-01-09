package com.upe.observatorio.projeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.projeto.domain.Curso;
import com.upe.observatorio.projeto.domain.CursoProjeto;
import com.upe.observatorio.projeto.domain.Projeto;

public interface CursoProjetoRepository extends JpaRepository<CursoProjeto, Long>{
	Optional<CursoProjeto> findByCursoAndProjeto(Curso curso, Projeto projeto);
}
