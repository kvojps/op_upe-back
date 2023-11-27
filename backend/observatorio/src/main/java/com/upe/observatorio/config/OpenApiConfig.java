package com.upe.observatorio.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(contact = @Contact(name = "OP-UPE", email = "lapes@upe.br", url = "https://github.com/kvojps/op_upe-back"), description = "Observatório de Projetos - Universidade de Pernambuco", title = "Observatório", version = "1.0", license = @License(name = "Licença da API", url = "#"), termsOfService = "Termos de serviço"), servers = {
		@Server(description = "Local ENV", url = "http://localhost:8080"), @Server(description = "Server ENV", url = "http://0.0.0.0:8080")}, security = {
		@SecurityRequirement(name = "bearerAuth")})
@SecurityScheme(name = "bearerAuth", description = "JWT auth description", scheme = "bearer", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {
}