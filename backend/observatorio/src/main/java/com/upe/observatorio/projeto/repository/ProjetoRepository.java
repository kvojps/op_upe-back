package com.upe.observatorio.projeto.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.projeto.domain.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long>{
	
	Page<Projeto> findAllByAreaTematicaContainingIgnoreCase(String areaTematica, Pageable pageable);
	
	Page<Projeto> findAllByModalidadeContainingIgnoreCase(String modalidade, Pageable pageable);
	
	Page<Projeto> findAllByTituloContainingIgnoreCase(String titulo, Pageable pageable);
}
