package isen.projet_dp_api.dao.email;

import org.springframework.mail.javamail.MimeMessageHelper;

public interface EmailServiceDAO {

    void sendEmail(MimeMessageHelper helper);
}
