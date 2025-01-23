package isen.projet_dp_api.dao.email.impl;

import isen.projet_dp_api.dao.email.EmailServiceDAO;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.utils.TestStrings;
import jakarta.mail.MessagingException;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
public class EmailServiceDAOMock implements EmailServiceDAO {

    @Override
    public void sendEmail(MimeMessageHelper helper) throws MessagingException {
        for (var recipient : helper.getMimeMessage().getAllRecipients()) {
            if (recipient.toString().equals(TestStrings.EMAIL_GOOD)) {
                return;
            }
        }
        throw new ApiException(TestStrings.ERROR_REGISTERING_USER, HttpStatus.BAD_REQUEST);
    }
}
