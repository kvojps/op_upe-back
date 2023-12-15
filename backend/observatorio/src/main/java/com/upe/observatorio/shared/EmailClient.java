package com.upe.observatorio.shared;

import com.upe.observatorio.projeto.model.dto.EmailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailClient {
    private final JavaMailSender mailSender;

    public void sendEmail(EmailDTO emailContent) {
        SimpleMailMessage email = new SimpleMailMessage();

        email.setFrom("lapesupe@gmail.com");
        email.setTo(emailContent.getReceiver());
        email.setSubject(emailContent.getSubject());
        email.setText(emailContent.getMessage());

        mailSender.send(email);
    }
}
