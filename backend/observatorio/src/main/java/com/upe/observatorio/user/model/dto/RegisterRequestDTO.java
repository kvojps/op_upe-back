package com.upe.observatorio.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

	@NotBlank(message = "Name not be empty")
	private String nome;

	@Email(message = "Email inválido")
	private String email;

	@NotBlank(message = "Password not be empty")
	private String senha;
}
