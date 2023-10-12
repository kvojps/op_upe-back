package com.upe.observatorio.usuario.servico;

import com.upe.observatorio.config.JwtService;
import com.upe.observatorio.usuario.dominio.Usuario;
import com.upe.observatorio.usuario.dominio.dto.AutenticacaoRequestDTO;
import com.upe.observatorio.usuario.dominio.dto.AutenticacaoResponseDTO;
import com.upe.observatorio.usuario.dominio.dto.CadastroRequestDTO;
import com.upe.observatorio.usuario.dominio.dto.UsuarioDTO;
import com.upe.observatorio.usuario.dominio.enums.Perfil;
import com.upe.observatorio.usuario.repositorio.UsuarioRepositorio;
import com.upe.observatorio.utils.ObservatorioExcecao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServico {

	private final UsuarioRepositorio repositorio;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;

	public List<Usuario> listarUsuarios() {
		return repositorio.findAll();
	}

	public Usuario buscarUsuarioPorId(@NotNull Long id) throws ObservatorioExcecao {
		return repositorio.findById(id).orElseThrow(() -> new
				ObservatorioExcecao("Não existe um usuário associado a este id"));
	}
	
	public AutenticacaoResponseDTO cadastrarUsuario(CadastroRequestDTO request) throws ObservatorioExcecao {
		if (repositorio.findByEmail(request.getEmail()).isPresent()) {
			throw new ObservatorioExcecao("Já existe um usuário cadastrado com esse e-mail!");
		}
		validarSenha(request.getSenha());

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

	private void validarSenha(String senha) throws ObservatorioExcecao {
		boolean comMaiuscula = false, comMinuscula = false, comNumerico = false, comEspecial = false;

		if (senha.length() < 8) {
			throw new ObservatorioExcecao("A senha informada deve ter 8 ou mais caracteres!");
		}

		for (char caracteres : senha.toCharArray()) {
			if (Character.isDigit(caracteres)) {
				comNumerico = true;
			} else if (Character.isUpperCase(caracteres)) {
				comMaiuscula = true;
			} else if (Character.isLowerCase(caracteres)) {
				comMinuscula = true;
			} else {
				comEspecial = true;
			}
		}

		if (!(comMaiuscula && comMinuscula && comNumerico && comEspecial)) {
			StringBuilder error = new StringBuilder("Senha inválida: A senha necessita de caracteres");

			if (!comMaiuscula) {
				error.append(" maiúsculos;");
			}
			if (!comMinuscula) {
				error.append(" minúsculos;");
			}
			if (!comNumerico) {
				error.append(" numéricos;");
			}
			if (!comEspecial) {
				error.append(" especiais;");
			}

			throw new ObservatorioExcecao(error.toString());
		}
	}
}
