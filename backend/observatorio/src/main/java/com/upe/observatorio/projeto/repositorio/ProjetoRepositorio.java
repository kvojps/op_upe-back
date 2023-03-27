package com.upe.observatorio.projeto.repositorio;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.projeto.dominio.Projeto;
import com.upe.observatorio.projeto.dominio.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.dominio.enums.ModalidadeEnum;

public interface ProjetoRepositorio extends JpaRepository<Projeto, Long> {

	List<Projeto> findAllByAreaTematica(AreaTematicaEnum areaTematica);

	List<Projeto> findAllByModalidade(ModalidadeEnum modalidade);

	List<Projeto> findAllByAreaTematicaAndModalidadeAndDataInicioAndDataFim(AreaTematicaEnum areaTematica,
			ModalidadeEnum modalidade, Date dataInicio, Date dataFim);

	List<Projeto> findAllByTituloContainingIgnoreCase(String titulo);
}
