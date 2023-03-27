package com.upe.observatorio.usuario.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.usuario.dominio.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>  {
	Optional<Usuario> findByEmail(String email);
}
