package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.exceptions.InternalServerException;
import com.nashtech.ecommercialwebsite.services.EmailSender;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static com.nashtech.ecommercialwebsite.utils.AppConstants.EMAIL;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailSender {

    private final JavaMailSender javaMailSender;

    @Override
    public void send(String to, String email) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom(EMAIL);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new InternalServerException("failed to send email", e);
        }
    }
}
