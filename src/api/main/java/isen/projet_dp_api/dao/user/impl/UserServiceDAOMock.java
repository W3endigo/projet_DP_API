package isen.projet_dp_api.dao.user.impl;

import isen.projet_dp_api.dao.user.UserServiceDAO;
import isen.projet_dp_api.model.dao.UserDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
public class UserServiceDAOMock implements UserServiceDAO {

    @Override
    public UserDAO registerUser(UserDAO userDAO) {
        // TODO Implement mock for tests
        return null;
    }
}
