package com.upe.observatorio.usuario.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.upe.observatorio.projeto.utilities.ObservatorioException;
import com.upe.observatorio.usuario.domain.Perfil;
import com.upe.observatorio.usuario.domain.Usuario;
import com.upe.observatorio.usuario.domain.dto.CriarPerfilUsuarioDTO;
import com.upe.observatorio.usuario.domain.dto.UsuarioDTO;
import com.upe.observatorio.usuario.repository.PerfilRepository;
import com.upe.observatorio.usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repositorio;
	
	@Autowired
	private PerfilRepository perfilRepositorio;

	public List<Usuario> listarUsuarios() {
		return repositorio.findAll();
	}

	public Optional<Usuario> buscarUsuarioPorId(Long id) {
		return repositorio.findById(id);
	}

	public Usuario adicionarUsuario(UsuarioDTO usuario) throws ObservatorioException {
		if (repositorio.findByEmail(usuario.getEmail()).isPresent()) {
			throw new ObservatorioException("Existe um usuário cadastrado com este e-mail");
		}
		
		ModelMapper modelMapper = new ModelMapper();
		Usuario usuarioSalvar = modelMapper.map(usuario, Usuario.class);
		
		return repositorio.save(usuarioSalvar);
	}

	public void atualizarUsuario(UsuarioDTO usuario, Long id) throws ObservatorioException{
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioException("Não existe um curso associado a este id");
		}
		
		Usuario usuarioExistente = repositorio.findById(id).get();
		if (!usuarioExistente.getNome().equals(usuario.getNome())) {
			usuarioExistente.setNome(usuario.getNome());
		}
		if (!usuarioExistente.getEmail().equals(usuario.getEmail())) {
			usuarioExistente.setEmail(usuario.getEmail());
		}
		if (!usuarioExistente.getSenha().equals(usuario.getSenha())) {
			usuarioExistente.setSenha(usuario.getNome());
		}
		if (!usuarioExistente.getMatricula().equals(usuario.getMatricula())) {
			usuarioExistente.setMatricula(usuario.getMatricula());
		}
		
		repositorio.save(usuarioExistente);
	}

	public void removerUsuario(Long id) throws ObservatorioException {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioException("Não existe um usuário associado a este id");
		}
		
		repositorio.deleteById(id);
	}
	
	public Usuario adicionarPerfisUsuario(CriarPerfilUsuarioDTO criarPerfilUsuarioDTO) throws ObservatorioException {
		Optional<Usuario> usuarioExistente = repositorio.findById(criarPerfilUsuarioDTO.getIdUsuario());
		List<Perfil> perfis = new ArrayList<>();
		
		if (usuarioExistente.isEmpty()) {
			throw new ObservatorioException("Não existe um usuário associado a este id");
		}
		
		perfis = criarPerfilUsuarioDTO.getIdPerfis().stream().map(id -> {
			Optional<Perfil> perfilAdicionar = perfilRepositorio.findById(id);
			if (perfilAdicionar.isPresent()) {
				return perfilAdicionar.get();
			}
			return null;
		}).collect(Collectors.toList());
		
		Usuario usuario = usuarioExistente.get();
		if (!perfis.isEmpty()) {
			usuario.setPerfis(perfis);
		}
		
		return repositorio.save(usuario);
	}

}
