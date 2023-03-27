package com.upe.observatorio.projeto.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.projeto.dominio.Curso;

public interface CursoRepositorio extends JpaRepository<Curso, Long>{
	
	Page<Curso> findAllByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
