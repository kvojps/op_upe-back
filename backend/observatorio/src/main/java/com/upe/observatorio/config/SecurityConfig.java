package com.upe.observatorio.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests(authorizationManagerRequest ->
                        authorizationManagerRequest
                                .requestMatchers(HttpMethod.GET, "/api/usuarios").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/campus/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/campus/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/campus/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/cursos/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/cursos/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/cursos/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/campus-curso/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/campus-curso/**").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/projetos/**").hasAnyAuthority("ADMIN", "COORDENADOR")
                                .requestMatchers(HttpMethod.PUT, "/api/projetos/**").hasAnyAuthority("ADMIN", "COORDENADOR")
                                .requestMatchers(HttpMethod.DELETE, "/api/projetos/**").hasAnyAuthority("ADMIN", "COORDENADOR")
                                .requestMatchers("/api/auth/verify-user/**").hasAnyAuthority("ADMIN", "COORDENADOR", "USUARIO")
                                .requestMatchers("/api/sheets/**").hasAnyAuthority("ADMIN", "COORDENADOR")
                                .requestMatchers("/api/auth", "/v3/**", "/swagger-ui/**").permitAll()
                                .requestMatchers("api/auth/forgot-password", "api/auth/reset-password").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                                .anyRequest()
                                .authenticated()
                ).exceptionHandling(httpSecurityExceptionHandlingConfigurer ->
                        httpSecurityExceptionHandlingConfigurer
                                .authenticationEntryPoint(authenticationEntryPoint())
                )
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configure(http));

        return http.build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return new CustomAuthenticationEntryPoint();
    }
}