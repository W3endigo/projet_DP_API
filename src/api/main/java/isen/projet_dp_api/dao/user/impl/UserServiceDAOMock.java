package isen.projet_dp_api.dao.user.impl;

import isen.projet_dp_api.dao.user.UserServiceDAO;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.dao.UserDAO;
import isen.projet_dp_api.utils.TestStrings;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
public class UserServiceDAOMock implements UserServiceDAO {

    @Override
    public UserDAO registerUser(UserDAO userDAO) {
        if (userDAO.getEmail().equals(TestStrings.EMAIL_GOOD)) {
            return userDAO;
        } else {
            throw new ApiException(TestStrings.ERROR_SEND_EMAIL, HttpStatus.BAD_REQUEST);
        }
    }
}
