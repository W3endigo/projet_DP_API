package isen.projet_dp_api.dao.email.impl;

import isen.projet_dp_api.dao.email.EmailServiceDAO;
import isen.projet_dp_api.utils.TestStrings;
import jakarta.mail.MessagingException;
import org.eclipse.angus.mail.util.MailConnectException;
import org.eclipse.angus.mail.util.SocketConnectException;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
public class EmailServiceDAOMock implements EmailServiceDAO {

    @Override
    public void sendEmail(MimeMessageHelper helper) throws MessagingException {
        for (var recipient : helper.getMimeMessage().getAllRecipients()) {
            if (recipient.toString().equals(TestStrings.EMAIL_HAROLD)) {
                return;
            }
        }
        throw new MailConnectException(new SocketConnectException("", new Exception(), "", 0, 0));

    }
}
