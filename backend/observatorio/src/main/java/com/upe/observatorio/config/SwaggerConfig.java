package com.upe.observatorio.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket swagger() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
			    .securityContexts(Collections.singletonList(securityContext()))
			    .securitySchemes(List.of(apiKey()))
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.upe.observatorio"))
				.paths(PathSelectors.any()).build();
	}

	@Bean
	UiConfiguration uiConfig() {
		return UiConfigurationBuilder.builder().operationsSorter(OperationsSorter.METHOD).build();
	}

	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "API-OPUPE",
	      "Aqui estão listados todos os endpoints disponíveis para uso.",
	      "1.0",
	      "#",
	      new Contact("José Ferreira dos Santos Júnior", "https://github.com/kvojps", "joseferreira.santosjunior@upe.br"),
	      "Licença da API",
	      "#",
	      Collections.emptyList());
	}
	
	private ApiKey apiKey() { 
	    return new ApiKey("JWT", "Authorization", "header"); 
	}
	
	private SecurityContext securityContext() { 
	    return SecurityContext.builder().securityReferences(defaultAuth()).build(); 
	} 

	private List<SecurityReference> defaultAuth() { 
	    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything"); 
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1]; 
	    authorizationScopes[0] = authorizationScope; 
	    return List.of(new SecurityReference("JWT", authorizationScopes));
	}
	
}
