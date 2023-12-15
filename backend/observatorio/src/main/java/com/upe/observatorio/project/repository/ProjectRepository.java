package com.upe.observatorio.project.repository;

import com.upe.observatorio.project.model.Projeto;
import com.upe.observatorio.project.model.enums.ThematicAreaEnum;
import com.upe.observatorio.project.model.enums.ModalityEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Projeto, Long> {
	Long countByAreaTematica(ThematicAreaEnum areaTematica);

	Long countByModalidade(ModalityEnum modalidade);

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
			@Param("areaTematica") ThematicAreaEnum areaTematica,
			@Param("modalidade") ModalityEnum modalidade,
			Pageable pageable
			);

	Optional<Projeto> findByTitulo(String titulo);
}
