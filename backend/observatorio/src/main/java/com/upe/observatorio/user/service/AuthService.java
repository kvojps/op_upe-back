package com.upe.observatorio.user.service;

import com.upe.observatorio.config.JwtService;
import com.upe.observatorio.project.model.dto.EmailDTO;
import com.upe.observatorio.shared.EmailClient;
import com.upe.observatorio.user.model.Usuario;
import com.upe.observatorio.user.model.dto.AuthRequestDTO;
import com.upe.observatorio.user.model.dto.AuthResponseDTO;
import com.upe.observatorio.user.model.dto.ResetPasswordDTO;
import com.upe.observatorio.user.repository.UserRepository;
import com.upe.observatorio.utils.ObservatoryException;
import com.upe.observatorio.utils.UserNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String SECRET_KEY = System.getenv("SECRET_KEY");

    private final UserRepository repositorio;
    private final EmailClient emailClient;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthResponseDTO submitUserLogin(AuthRequestDTO request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getSenha()));

        Usuario user = repositorio.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponseDTO.builder().token(jwtToken).build();
    }

    public void forgotPassword(String email) {
        String jwtToken = getJwtToken(email, "reset_password");

        EmailDTO emailContent = new EmailDTO();
        emailContent.setSubject("Recuperação de senha");
        emailContent.setMessage("Token de recuperação de senha: " + jwtToken);
        emailContent.setReceiver(email);

        emailClient.sendEmail(emailContent);
    }

    public void resetPassword(ResetPasswordDTO resetPasswordDTO) {
        Claims claims = extractClaims(resetPasswordDTO.getResetToken());

        String tokenUserEmail = claims.get("email", String.class);
        String tokenType = claims.get("type", String.class);

        if (!"reset_password".equals(tokenType)) {
            throw new ObservatoryException("Invalid token_type");
        }

        UserService.validatePassword(resetPasswordDTO.getNewPassword());
        Usuario userToUpdate = repositorio.findByEmail(tokenUserEmail).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        userToUpdate.setSenha(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));

        repositorio.save(userToUpdate);
    }

    public void sendVerificationEmail(String email) {
        String jwtToken = getJwtToken(email, "verify_email");

        EmailDTO emailContent = new EmailDTO();
        emailContent.setSubject("Verificação de e-mail");
        emailContent.setMessage("Token de verificação de e-mail: " + jwtToken);
        emailContent.setReceiver(email);

        emailClient.sendEmail(emailContent);
    }

    public void verifyUser(String email, String verificationToken) {
        Claims claims = extractClaims(verificationToken);

        String tokenUserEmail = claims.get("email", String.class);
        String tokenType = claims.get("type", String.class);

        if (!"verify_email".equals(tokenType)) {
            throw new ObservatoryException("Invalid token_type");
        }
        if (!tokenUserEmail.equals(email)) {
            throw new ObservatoryException("Invalid email");
        }

        Usuario user = repositorio.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException("User not found"));
        user.setVerificado(true);

        repositorio.save(user);
    }

    private String getJwtToken(String email, String tokenType) {
        Map<String, Object> tokenPayload = new HashMap<>();
        tokenPayload.put("email", email);
        tokenPayload.put("type", tokenType);
        tokenPayload.put("exp", new Date(System.currentTimeMillis() + 3600000));

        return Jwts.builder()
                .addClaims(tokenPayload)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    private Claims extractClaims(String token) {
        Claims claims;
        try{
            claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new ObservatoryException("Invalid JWT: " + e.getMessage());
        }

        return claims;
    }
}
