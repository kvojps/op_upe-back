package com.upe.observatorio.publicacao.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.publicacao.dominio.Publicacao;

public interface PublicacaoRepositorio extends JpaRepository<Publicacao, Long> {

}
