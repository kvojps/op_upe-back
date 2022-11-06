package com.upe.observatorio.usuario.service;

import java.util.List;
import java.util.Optional;

import com.upe.observatorio.usuario.domain.Perfil;
import com.upe.observatorio.usuario.domain.dto.PerfilDTO;

public interface PerfilService {
	
	public List<Perfil> listarPerfis();
	
	public Optional<Perfil> buscarPerfilPorId(Long id);
	
	public Perfil adicionarPerfil(PerfilDTO perfil);
	
	public void atualizarPerfil(PerfilDTO perfil, Long id);
	
	public void removerPerfil(Long id);
}
