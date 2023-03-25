package com.upe.observatorio.projeto.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.projeto.domain.Projeto;
import com.upe.observatorio.projeto.domain.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.domain.enums.ModalidadeEnum;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

	List<Projeto> findAllByAreaTematica(AreaTematicaEnum areaTematica);

	List<Projeto> findAllByModalidade(ModalidadeEnum modalidade);

	List<Projeto> findAllByAreaTematicaAndModalidadeAndDataInicioAndDataFim(AreaTematicaEnum areaTematica,
			ModalidadeEnum modalidade, Date dataInicio, Date dataFim);

	List<Projeto> findAllByTituloContainingIgnoreCase(String titulo);
}
