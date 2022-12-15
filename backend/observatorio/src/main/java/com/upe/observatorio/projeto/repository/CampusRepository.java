package com.upe.observatorio.projeto.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.projeto.domain.Campus;

public interface CampusRepository extends JpaRepository<Campus, Long> {

	Page<Campus> findAllByNomeContainingIgnoreCase(String nome, Pageable pageable);
	
	Page<Campus> findAllByCidadeContainingIgnoreCase(String cidade, Pageable pageable);
	
	
}
