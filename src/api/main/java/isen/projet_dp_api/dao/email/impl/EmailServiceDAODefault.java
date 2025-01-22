package isen.projet_dp_api.dao.email.impl;

import isen.projet_dp_api.dao.email.EmailServiceDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!test")
public class EmailServiceDAODefault implements EmailServiceDAO {

    private final JavaMailSender emailSender;

    public EmailServiceDAODefault(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void sendEmail(MimeMessageHelper helper) {
        emailSender.send(helper.getMimeMessage());
    }
}
