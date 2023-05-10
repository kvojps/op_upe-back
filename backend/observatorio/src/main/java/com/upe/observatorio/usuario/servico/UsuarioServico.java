package com.upe.observatorio.usuario.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.upe.observatorio.config.JwtService;
import com.upe.observatorio.usuario.dominio.Usuario;
import com.upe.observatorio.usuario.dominio.dto.AutenticacaoRequestDTO;
import com.upe.observatorio.usuario.dominio.dto.AutenticacaoResponseDTO;
import com.upe.observatorio.usuario.dominio.dto.CadastroRequestDTO;
import com.upe.observatorio.usuario.dominio.dto.UsuarioDTO;
import com.upe.observatorio.usuario.dominio.enums.Perfil;
import com.upe.observatorio.usuario.repositorio.UsuarioRepositorio;
import com.upe.observatorio.utils.ObservatorioExcecao;

@Service
public class UsuarioServico {
	
	@Autowired
	private UsuarioRepositorio repositorio;
	
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
	
	public AutenticacaoResponseDTO cadastrarUsuario(CadastroRequestDTO request) throws ObservatorioExcecao {
		if (request.getSenha().length() < 8) {
			throw new ObservatorioExcecao("A senha deve conter 8 caracteres ou mais!");
		}
		
		if (repositorio.findByEmail(request.getEmail()).isPresent()) {
			throw new ObservatorioExcecao("Já existe um usuário cadastrado com esse e-mail!");
		}
		
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

	public void atualizarUsuario(UsuarioDTO usuario, Long id) throws ObservatorioExcecao{
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um curso associado a este id");
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

	public void removerUsuario(Long id) throws ObservatorioExcecao {
		if (repositorio.findById(id).isEmpty()) {
			throw new ObservatorioExcecao("Não existe um usuário associado a este id");
		}
		
		repositorio.deleteById(id);
	}
}
