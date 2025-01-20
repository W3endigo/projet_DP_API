package isen.projet_dp_api.service;

import isen.projet_dp_api.enums.EmailTypes;
import isen.projet_dp_api.enums.PicturesTypes;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class EmailService {

    private final JavaMailSender emailSender;

    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String emailFrom;

    public EmailService(JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
    }

    public void sendEmail(String toEmail, String subject, String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailFrom);
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

        helper.setFrom(emailFrom);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(html, true);

        emailSender.send(message);

        log.info("MessageTemplate sent successfully");
    }

public void sendEmailTemplatePicture(String toEmail, EmailTypes emailTypes, Context context, Optional<List<PicturesTypes>> pictures) {
    try {
        var message = emailSender.createMimeMessage();
        var helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        var html = templateEngine.process(emailTypes.getTemplateName(), context);


        helper.setTo(toEmail);
        helper.setText(templateEngine.process(emailTypes.getTemplateName(), context), true);
        helper.setSubject(emailTypes.getSubject());
        helper.setFrom(emailFrom);

        // Tout les mails auront le logo en image inline
        helper.addInline(PicturesTypes.LOGO.getImageVarName(), new FileSystemResource(new File(PicturesTypes.LOGO.getImagePath())));

        // Ajout d'autres images inline si elles ont été passées en paramètre
        if (pictures.isPresent()) {
            for (var picture : pictures.get()) {
                helper.addInline(picture.getImageVarName(), new FileSystemResource(new File(picture.getImagePath())));
            }
        }

        emailSender.send(message);
        log.info("{} sent successfully", emailTypes.getSubject());
    } catch (MessagingException e) {
        log.error("Error sending email with template and pictures", e);
    }
}
}