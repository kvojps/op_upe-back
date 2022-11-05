package com.upe.observatorio.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.projeto.domain.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long>{

}
