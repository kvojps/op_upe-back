package com.upe.observatorio.publicacao.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.projeto.dominio.enums.AreaTematicaEnum;
import com.upe.observatorio.publicacao.dominio.Publicacao;

public interface PublicacaoRepositorio extends JpaRepository<Publicacao, Long> {
	List<Publicacao> findByProjetoAreaTematica(AreaTematicaEnum categoria);
}
