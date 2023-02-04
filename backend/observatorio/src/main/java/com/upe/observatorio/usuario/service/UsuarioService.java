package com.upe.observatorio.usuario.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.upe.observatorio.config.JwtService;
import com.upe.observatorio.usuario.domain.Usuario;
import com.upe.observatorio.usuario.domain.dto.AutenticacaoRequestDTO;
import com.upe.observatorio.usuario.domain.dto.AutenticacaoResponseDTO;
import com.upe.observatorio.usuario.domain.dto.CadastroRequestDTO;
import com.upe.observatorio.usuario.domain.dto.UsuarioDTO;
import com.upe.observatorio.usuario.domain.enums.Perfil;
import com.upe.observatorio.usuario.repository.UsuarioRepository;
import com.upe.observatorio.utils.ObservatorioException;

@Service
public class UsuarioService {
	
	@Autowired
	private UsuarioRepository repositorio;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	public List<Usuario> listarUsuarios() {
		return repositorio.findAll();
	}

	public Optional<Usuario> buscarUsuarioPorId(Long id) {
		return repositorio.findById(id);
	}
	
	public AutenticacaoResponseDTO cadastrarUsuario(CadastroRequestDTO request) {
		Usuario usuario = new Usuario();
		usuario.setNome(request.getNome());
		usuario.setEmail(request.getEmail());
		usuario.setSenha(passwordEncoder.encode(request.getSenha()));
		usuario.setPerfil(Perfil.USUARIO);
		repositorio.save(usuario);

		var jwtToken = jwtService.generateToken(usuario);
		return AutenticacaoResponseDTO.builder().token(jwtToken).build();
	}
	
	public AutenticacaoResponseDTO loginUsuario(AutenticacaoRequestDTO request) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha()));

		Usuario usuario = repositorio.findByEmail(request.getEmail()).orElseThrow();
		var jwtToken = jwtService.generateToken(usuario);
		return AutenticacaoResponseDTO.builder().token(jwtToken).build();
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
}
