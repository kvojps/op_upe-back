package com.upe.observatorio.projeto.repositorio;

import com.upe.observatorio.projeto.dominio.Projeto;
import com.upe.observatorio.projeto.dominio.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.dominio.enums.ModalidadeEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Projeto, Long> {
	Long countByAreaTematica(AreaTematicaEnum areaTematica);

	Long countByModalidade(ModalidadeEnum modalidade);

	@Query("SELECT p FROM Projeto p WHERE (DATE(:dataInicio) IS NULL OR p.dataInicio >= DATE(:dataInicio))" +
			"AND (DATE(:dataFim) IS NULL OR p.dataFim <= DATE(:dataFim)) " +
			"AND (:titulo IS NULL OR UPPER(p.titulo) LIKE '%' || UPPER(:titulo) || '%') " +
			"AND (:areaTematica IS NULL OR p.areaTematica = :areaTematica) " +
			"AND (:modalidade IS NULL or p.modalidade = :modalidade) " +
			"ORDER BY p.dataInicio DESC")
	Page<Projeto> findWithFilters(
			@Param("dataInicio")String dataInicio,
			@Param("dataFim")String dataFim,
			@Param("titulo") String titulo,
			@Param("areaTematica") AreaTematicaEnum areaTematica,
			@Param("modalidade") ModalidadeEnum modalidade,
			Pageable pageable
			);

	Optional<Projeto> findByTitulo(String titulo);
}
