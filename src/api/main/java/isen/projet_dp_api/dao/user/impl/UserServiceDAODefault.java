package isen.projet_dp_api.dao.user.impl;

import isen.projet_dp_api.dao.user.UserServiceDAO;
import isen.projet_dp_api.model.dao.UserDAO;
import isen.projet_dp_api.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
@Profile("!test")
public class UserServiceDAODefault implements UserServiceDAO {

    private final UserRepository userRepository;

    public UserServiceDAODefault(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDAO registerUser(UserDAO userDAO) {
        try {
            return userRepository.save(userDAO);
        } catch (JpaObjectRetrievalFailureException e) {
            log.error(e.getMessage());
            // TODO Rethrow custom exception to be caught by the controller and return a 409 status code
        }
        return null;
    }
}