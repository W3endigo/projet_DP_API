package isen.projet_dp_api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Service
@Log4j2
public class EmailService {

    private final JavaMailSender emailSender;

    private final SpringTemplateEngine templateEngine;

    public EmailService(JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmail(String toEmail, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("labmanagerresearch@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        emailSender.send(message);

        log.info("Message sent successfully");
    }

    public void sendEmailTemplate(String toEmail, String subject, String templateName, Context context) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        String html = templateEngine.process(templateName, context);

        helper.setTo(toEmail);
        helper.setText(html, true);
        helper.setSubject(subject);
        helper.setFrom("labmanagerresearch@gmail.com");

        emailSender.send(message);

        log.info("MessageTemplate sent successfully");
    }

    public void sendEmailTemplatePicture(String toEmail, String subject, String templateName, Context context, String imagePath, String imageResourceName) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        String html = templateEngine.process(templateName, context);

        helper.setTo(toEmail);
        helper.setText(html, true);
        helper.setSubject(subject);
        helper.setFrom("labmanagerresearch@gmail.com");

        // Add inline image
        FileSystemResource res = new FileSystemResource(new File(imagePath));
        helper.addInline(imageResourceName, res);

        emailSender.send(message);

        log.info("MessageTemplatePicture sent successfully");
    }
}