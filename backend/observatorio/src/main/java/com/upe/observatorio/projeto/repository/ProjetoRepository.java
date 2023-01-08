package com.upe.observatorio.projeto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.projeto.domain.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto, Long>{
	
	List<Projeto> findAllByAreaTematicaContainingIgnoreCase(String areaTematica);
	
	List<Projeto> findAllByModalidadeContainingIgnoreCase(String modalidade);
	
	List<Projeto> findAllByTituloContainingIgnoreCase(String titulo);
}
