package isen.projet_dp_api.dao.user.impl;

import isen.projet_dp_api.dao.user.UserServiceDAO;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.dao.UserDAO;
import isen.projet_dp_api.repository.UserRepository;
import isen.projet_dp_api.utils.exception.ErrorMessage;
import isen.projet_dp_api.utils.exception.LogExceptionUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        if (userRepository.existsById(userDAO.getEmail())) {
            LogExceptionUtils.logException(this.getClass(), String.format(ErrorMessage.ERROR_USER_ALREADY_EXIST,  userDAO.getEmail()), null,  userDAO.getEmail());
            throw new ApiException(String.format(ErrorMessage.ERROR_USER_ALREADY_EXIST, userDAO.getEmail()), HttpStatus.CONFLICT);
        }
        try {
            return userRepository.save(userDAO);
        } catch (JpaObjectRetrievalFailureException e) {
            LogExceptionUtils.logException(this.getClass(), ErrorMessage.ERROR_REGISTERING_USER + ErrorMessage.ERROR_FOREIGN_KEY_NOT_FOUND, e, userDAO);
            throw new ApiException(e, ErrorMessage.ERROR_REGISTERING_USER + String.format(ErrorMessage.ERROR_COMPANY_NOT_FOUND, userDAO.getName().getName()), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public UserDAO getUserByEmail(String email) {
        return userRepository.findById(email).orElseThrow(() -> new UsernameNotFoundException(String.format(ErrorMessage.ERROR_USER_NOT_FOUND, email)));
    }

    @Override
    public UserDAO updateUser(UserDAO userDAO) {
        if (!userRepository.existsById(userDAO.getEmail())) {
            LogExceptionUtils.logException(this.getClass(), String.format(ErrorMessage.ERROR_USER_NOT_FOUND, userDAO.getEmail()), null, userDAO.getEmail());
            throw new ApiException(String.format(ErrorMessage.ERROR_USER_NOT_FOUND, userDAO.getEmail()), HttpStatus.NOT_FOUND);
        }
        try {
            return userRepository.save(userDAO);
        } catch (JpaObjectRetrievalFailureException e) {
            LogExceptionUtils.logException(this.getClass(), ErrorMessage.ERROR_UPDATING_USER + ErrorMessage.ERROR_FOREIGN_KEY_NOT_FOUND, e, userDAO);
            throw new ApiException(e, ErrorMessage.ERROR_UPDATING_USER + String.format(ErrorMessage.ERROR_COMPANY_NOT_FOUND, userDAO.getName().getName()), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void deleteUser(UserDAO userDAO) {
        if (!userRepository.existsById(userDAO.getEmail())) {
            LogExceptionUtils.logException(this.getClass(), String.format(ErrorMessage.ERROR_USER_NOT_FOUND,  userDAO.getEmail()), null,  userDAO.getEmail());
            throw new ApiException(String.format(ErrorMessage.ERROR_USER_NOT_FOUND, userDAO.getEmail()), HttpStatus.CONFLICT);
        }
        if (userDAO.getParticipants() != null && userDAO.getParticipants().size() > 0) {
            LogExceptionUtils.logException(this.getClass(), String.format(ErrorMessage.ERROR_USER_HAS_PROJECTS,  userDAO.getEmail()), null,  userDAO.getEmail());
            throw new ApiException(String.format(ErrorMessage.ERROR_USER_HAS_PROJECTS, userDAO.getEmail()), HttpStatus.CONFLICT);
        }
        try {
            userRepository.delete(userDAO);
        } catch (JpaObjectRetrievalFailureException e) {
            LogExceptionUtils.logException(this.getClass(), ErrorMessage.ERROR_DELETING_USER + ErrorMessage.ERROR_FOREIGN_KEY_NOT_FOUND, e, userDAO);
            throw new ApiException(e, ErrorMessage.ERROR_DELETING_USER + String.format(ErrorMessage.ERROR_COMPANY_NOT_FOUND, userDAO.getName().getName()), HttpStatus.BAD_REQUEST);
        }
    }
}