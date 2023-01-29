package com.upe.observatorio.publicacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.publicacao.domain.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long>{

}
