package com.upe.observatorio.usuario.servico;

import com.upe.observatorio.config.JwtService;
import com.upe.observatorio.usuario.dominio.Usuario;
import com.upe.observatorio.usuario.dominio.dto.AutenticacaoResponseDTO;
import com.upe.observatorio.usuario.dominio.dto.CadastroRequestDTO;
import com.upe.observatorio.usuario.dominio.dto.UsuarioDTO;
import com.upe.observatorio.usuario.dominio.enums.Perfil;
import com.upe.observatorio.usuario.repositorio.UsuarioRepositorio;
import com.upe.observatorio.utils.InvalidPasswordException;
import com.upe.observatorio.utils.ObservatoryException;
import com.upe.observatorio.utils.UserNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServico {

	private final UsuarioRepositorio repositorio;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	public List<Usuario> listarUsuarios() {
		return repositorio.findAll();
	}

	public Usuario buscarUsuarioPorId(@NotNull Long id) {
		return repositorio.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
	}
	
	public AutenticacaoResponseDTO cadastrarUsuario(CadastroRequestDTO request)  {
		if (repositorio.findByEmail(request.getEmail()).isPresent()) {
			throw new ObservatoryException("Já existe um usuário cadastrado com esse e-mail!");
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

	public void atualizarUsuario(UsuarioDTO usuario, Long id) {
		if (repositorio.findById(id).isEmpty()) {
			throw new UserNotFoundException("User not found");
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

	public void removerUsuario(Long id) {
		if (repositorio.findById(id).isEmpty()) {
			throw new UserNotFoundException("User not found");
		}
		
		repositorio.deleteById(id);
	}

	public static void validarSenha(String senha) {
		boolean comMaiuscula = false, comMinuscula = false, comNumerico = false, comEspecial = false;

		if (senha.length() < 8) {
			throw new InvalidPasswordException("Password must be longer than 8 characters");
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

			throw new InvalidPasswordException(error.toString());
		}
	}
}
