package com.upe.observatorio.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.observatorio.user.model.Usuario;

public interface UserRepository extends JpaRepository<Usuario, Long>  {
	Optional<Usuario> findByEmail(String email);
}
