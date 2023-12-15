package com.upe.observatorio.user.repositorio;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.user.dominio.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>  {
	Optional<Usuario> findByEmail(String email);
}
