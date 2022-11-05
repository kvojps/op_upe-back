package com.upe.observatorio.usuario.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.usuario.domain.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>  {

}
