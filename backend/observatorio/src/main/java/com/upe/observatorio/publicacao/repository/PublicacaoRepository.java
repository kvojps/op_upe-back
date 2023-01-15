package com.upe.observatorio.publicacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.publicacao.domain.Publicacao;

public interface PublicacaoRepository extends JpaRepository<Publicacao, Long> {

}
