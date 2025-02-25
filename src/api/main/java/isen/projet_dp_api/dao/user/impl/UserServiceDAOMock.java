package isen.projet_dp_api.dao.user.impl;

import isen.projet_dp_api.dao.user.UserServiceDAO;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.dao.CompanyDAO;
import isen.projet_dp_api.model.dao.UserDAO;
import isen.projet_dp_api.utils.TestStrings;
import isen.projet_dp_api.utils.exception.ErrorMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
public class UserServiceDAOMock implements UserServiceDAO {

    @Override
    public UserDAO registerUser(UserDAO userDAO) {
        if (userDAO.getEmail().equals(TestStrings.EMAIL_HAROLD) || userDAO.getEmail().equals(TestStrings.EMAIL_ASTRID)) {
            return userDAO;
        } else {
            throw new ApiException(ErrorMessage.ERROR_REGISTERING_USER, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public UserDAO getUserByEmail(String email) {
        if (email.equals(TestStrings.EMAIL_HAROLD) || email.equals(TestStrings.EMAIL_ASTRID)) {
            return new UserDAO(email, new BCryptPasswordEncoder().encode(TestStrings.PASSWORD), TestStrings.FIRST_NAME, TestStrings.LAST_NAME, new CompanyDAO(TestStrings.COMPANY));
        } else {
            throw new UsernameNotFoundException(String.format(ErrorMessage.ERROR_USER_NOT_FOUND, email));
        }
    }

    @Override
    public UserDAO updateUser(UserDAO userDAO) {
        if (userDAO.getEmail().equals(TestStrings.EMAIL_HAROLD)) {
            return userDAO;
        } else {
            throw new UsernameNotFoundException(String.format(ErrorMessage.ERROR_USER_NOT_FOUND, userDAO.getEmail()));
        }
    }
}
