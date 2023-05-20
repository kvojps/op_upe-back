package com.upe.observatorio.projeto.repositorio;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.upe.observatorio.projeto.dominio.Projeto;
import com.upe.observatorio.projeto.dominio.enums.AreaTematicaEnum;
import com.upe.observatorio.projeto.dominio.enums.ModalidadeEnum;
import com.upe.observatorio.usuario.dominio.Usuario;

public interface ProjetoRepositorio extends JpaRepository<Projeto, Long> {

	List<Projeto> findAllByAreaTematica(AreaTematicaEnum areaTematica);

	List<Projeto> findAllByModalidade(ModalidadeEnum modalidade);

	Page<Projeto> findAllByTituloContainingIgnoreCase(String titulo, Pageable pageable);
	
	Optional<Projeto> findByTituloContainingIgnoreCase(String titulo);
	
	@Query("SELECT p FROM Projeto p ORDER BY p.dataFim DESC")
	List<Projeto> findAllOrderByDataFimDesc();
	
    @Query("SELECT p FROM Projeto p WHERE p.publicacao IS NULL")
    List<Projeto> findProjetosWithPublicacaoNull();
    
    List<Projeto> findByPublicacaoIsNullAndUsuario(Usuario usuario);
}
