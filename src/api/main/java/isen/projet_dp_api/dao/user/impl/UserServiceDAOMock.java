package isen.projet_dp_api.dao.user.impl;

import isen.projet_dp_api.dao.user.UserServiceDAO;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.dao.UserDAO;
import isen.projet_dp_api.utils.TestStrings;
import isen.projet_dp_api.utils.exception.ErrorMessage;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
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
        // TODO
        return null;
    }

    @Override
    public UserDAO updateUser(UserDAO userDAO) {
        // TODO
        return null;
    }
}
