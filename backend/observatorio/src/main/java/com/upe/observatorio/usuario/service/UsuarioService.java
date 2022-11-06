package com.upe.observatorio.usuario.service;

import java.util.List;
import java.util.Optional;

import com.upe.observatorio.usuario.domain.Usuario;
import com.upe.observatorio.usuario.domain.dto.UsuarioDTO;

public interface UsuarioService {

	public List<Usuario> listarUsuarios();
	
	public Optional<Usuario> buscarUsuarioPorId(Long id);
	
	public Usuario adicionarUsuario(UsuarioDTO usuario);
	
	public void atualizarUsuario(UsuarioDTO usuario, Long id);
	
	public void removerUsuario(Long id);
}
