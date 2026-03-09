package com.secure.notes.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendPasswordResetEmail(String to, String username, String resetUrl) {

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("Secure Notes - Password Reset");

            String htmlContent = """
                <div style="font-family: Arial, sans-serif; max-width:600px; margin:auto;">
                
                    <h2 style="color:#2c3e50;">Secure Notes</h2>
                    
                    <p>Hello <b>%s</b>,</p>
                    
                    <p>You requested a password reset for your Secure Notes account.</p>
                    
                    <p>This link will expire in <b>1 hour</b>.</p>
                    
                    <a href="%s"
                       style="
                       display:inline-block;
                       padding:12px 20px;
                       background-color:#3498db;
                       color:white;
                       text-decoration:none;
                       border-radius:5px;
                       font-weight:bold;">
                       Reset Password
                    </a>
                    
                    <p style="margin-top:20px;">
                    If you did not request this password reset, please ignore this email.
                    </p>
                    
                    <hr>
                    
                    <p style="font-size:12px;color:gray;">
                    Secure Notes • Personal Encrypted Notes Application
                    </p>
                
                </div>
                """.formatted(username, resetUrl);

            helper.setText(htmlContent, true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email");
        }
    }

}
