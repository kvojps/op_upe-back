package com.upe.observatorio.projeto.repositorio;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.projeto.dominio.Campus;

public interface CampusRepositorio extends JpaRepository<Campus, Long> {

	Page<Campus> findAllByNomeContainingIgnoreCase(String nome, Pageable pageable);
	
	Page<Campus> findAllByCidadeContainingIgnoreCase(String cidade, Pageable pageable);
	
	
}
