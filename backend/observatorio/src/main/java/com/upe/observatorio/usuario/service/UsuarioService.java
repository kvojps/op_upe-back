package com.upe.observatorio.usuario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.upe.observatorio.usuario.domain.Usuario;
import com.upe.observatorio.usuario.domain.dto.UsuarioDTO;

@Service
public class UsuarioService {

	public List<Usuario> listarUsuarios() {
		// TODO Auto-generated method stub
		return null;
	}

	public Optional<Usuario> buscarUsuarioPorId(Long id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	public Usuario adicionarUsuario(UsuarioDTO usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	public void atualizarUsuario(UsuarioDTO usuario, Long id) {
		// TODO Auto-generated method stub
	}

	public void removerUsuario(Long id) {
		// TODO Auto-generated method stub
	}

}
