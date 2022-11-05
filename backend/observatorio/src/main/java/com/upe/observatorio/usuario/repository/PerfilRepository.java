package com.upe.observatorio.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.usuario.domain.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {

}
