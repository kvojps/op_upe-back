package com.upe.observatorio.user.service;

import com.upe.observatorio.config.JwtService;
import com.upe.observatorio.user.model.Usuario;
import com.upe.observatorio.user.model.dto.AuthResponseDTO;
import com.upe.observatorio.user.model.dto.RegisterRequestDTO;
import com.upe.observatorio.user.model.dto.UserDTO;
import com.upe.observatorio.user.model.enums.Role;
import com.upe.observatorio.user.repository.UserRepository;
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
public class UserService {

	private final UserRepository repository;
	private final PasswordEncoder passwordEncoder;
	private final JwtService jwtService;

	public List<Usuario> readUsers() {
		return repository.findAll();
	}

	public Usuario findUserById(@NotNull Long id) {
		return repository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
	}
	
	public AuthResponseDTO createUser(RegisterRequestDTO request)  {
		if (repository.findByEmail(request.getEmail()).isPresent()) {
			throw new ObservatoryException("Já existe um usuário cadastrado com esse e-mail!");
		}
		validatePassword(request.getSenha());

		Usuario usuario = new Usuario();
		usuario.setNome(request.getNome());
		usuario.setEmail(request.getEmail());
		usuario.setSenha(passwordEncoder.encode(request.getSenha()));
		usuario.setPerfil(Role.USUARIO);
		repository.save(usuario);

		var jwtToken = jwtService.generateToken(usuario);
		return AuthResponseDTO.builder().token(jwtToken).build();
	}

	public void updateUser(UserDTO user, Long id) {
		if (repository.findById(id).isEmpty()) {
			throw new UserNotFoundException("User not found");
		}
		
		Usuario existentUser = repository.findById(id).get();
		if (!existentUser.getNome().equals(user.getNome())) {
			existentUser.setNome(user.getNome());
		}
		if (!existentUser.getEmail().equals(user.getEmail())) {
			existentUser.setEmail(user.getEmail());
		}
		if (!existentUser.getSenha().equals(user.getSenha())) {
			existentUser.setSenha(user.getNome());
		}
		if (!existentUser.getMatricula().equals(user.getMatricula())) {
			existentUser.setMatricula(user.getMatricula());
		}
		
		repository.save(existentUser);
	}

	public void deleteUser(Long id) {
		if (repository.findById(id).isEmpty()) {
			throw new UserNotFoundException("User not found");
		}
		
		repository.deleteById(id);
	}

	public static void validatePassword(String password) {
		boolean withUppercase = false, withLowercase = false, withNumber = false, withSpecial = false;

		if (password.length() < 8) {
			throw new InvalidPasswordException("Password must be longer than 8 characters");
		}

		for (char character : password.toCharArray()) {
			if (Character.isDigit(character)) {
				withNumber = true;
			} else if (Character.isUpperCase(character)) {
				withUppercase = true;
			} else if (Character.isLowerCase(character)) {
				withLowercase = true;
			} else {
				withSpecial = true;
			}
		}

		if (!(withUppercase && withLowercase && withNumber && withSpecial)) {
			StringBuilder error = new StringBuilder("Password need chars: ");

			if (!withUppercase) {
				error.append("uppercase;");
			}
			if (!withLowercase) {
				error.append("lowercase;");
			}
			if (!withNumber) {
				error.append("number;");
			}
			if (!withSpecial) {
				error.append("special;");
			}

			throw new InvalidPasswordException(error.toString());
		}
	}
}
