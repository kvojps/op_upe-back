package com.upe.observatorio.projeto.repositorio;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.upe.observatorio.projeto.dominio.Projeto;
import com.upe.observatorio.projeto.dominio.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.dominio.enums.ModalidadeEnum;

public interface ProjetoRepositorio extends JpaRepository<Projeto, Long> {

	List<Projeto> findAllByAreaTematica(AreaTematicaEnum areaTematica);

	List<Projeto> findAllByModalidade(ModalidadeEnum modalidade);

	Page<Projeto> findAllByTituloContainingIgnoreCase(String titulo, Pageable pageable);
	
	@Query("SELECT p FROM Projeto p ORDER BY p.dataFim DESC")
	List<Projeto> findAllOrderByDataFimDesc();
	
    @Query("SELECT p FROM Projeto p WHERE p.publicacao IS NULL")
    List<Projeto> findProjetosWithPublicacaoNull();
}
