package isen.projet_dp_api.service;

import isen.projet_dp_api.dao.email.EmailServiceDAO;
import isen.projet_dp_api.enums.EmailTypes;
import isen.projet_dp_api.enums.PicturesTypes;
import isen.projet_dp_api.utils.ApiStrings;
import isen.projet_dp_api.utils.exception.ErrorMessage;
import isen.projet_dp_api.utils.exception.LogExceptionUtils;
import lombok.extern.log4j.Log4j2;
import org.eclipse.angus.mail.util.MailConnectException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
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

    private final EmailServiceDAO emailServiceDAO;

    @Value("${spring.mail.username}")
    private String emailFrom;

    public EmailService(JavaMailSender emailSender, SpringTemplateEngine templateEngine, EmailServiceDAO emailServiceDAO) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
        this.emailServiceDAO = emailServiceDAO;
    }

    public Optional<String> sendEmailTemplatePicture(String toEmail, EmailTypes emailTypes, Context context, Optional<List<PicturesTypes>> pictures) {
        try {
            log.info(ApiStrings.SENDING_EMAIL, toEmail, emailTypes.getTemplateName());

            var message = emailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

            helper.setTo(toEmail);
            helper.setText(templateEngine.process(emailTypes.getTemplateName(), context), true);
            helper.setSubject(emailTypes.getSubject());
            helper.setFrom(emailFrom);

            // Add logo image inline
            helper.addInline(PicturesTypes.LOGO.getImageVarName(), new FileSystemResource(new File(PicturesTypes.LOGO.getImagePath())));

            // Add other images inline if provided
            if (pictures.isPresent()) {
                for (var picture : pictures.get()) {
                    helper.addInline(picture.getImageVarName(), new FileSystemResource(new File(picture.getImagePath())));
                }
            }

            emailServiceDAO.sendEmail(helper);
            log.info(ApiStrings.EMAIL_SENT);
            return Optional.empty();
        } catch (MailConnectException e) {
            LogExceptionUtils.logException(this.getClass(), ErrorMessage.ERROR_SEND_EMAIL + ErrorMessage.ERROR_CONNECT_SMTP, e);
            return Optional.of(ErrorMessage.ERROR_SEND_EMAIL + ErrorMessage.ERROR_CONNECT_SMTP);
        } catch (MessagingException e) {
            LogExceptionUtils.logException(this.getClass(), ErrorMessage.ERROR_SEND_EMAIL + ErrorMessage.ERROR_RENDERING_EMAIL, e);
            return Optional.of(ErrorMessage.ERROR_SEND_EMAIL + ErrorMessage.ERROR_RENDERING_EMAIL);
        }
    }
}
