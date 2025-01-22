package isen.projet_dp_api.dao.email.impl;

import isen.projet_dp_api.dao.email.EmailServiceDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
public class EmailServiceDAOMock implements EmailServiceDAO {

    @Override
    public void sendEmail(MimeMessageHelper helper) {
        // TODO: mock ?
    }
}
