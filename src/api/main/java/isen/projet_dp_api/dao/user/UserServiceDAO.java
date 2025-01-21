package isen.projet_dp_api.dao.user;

import isen.projet_dp_api.model.dao.UserDAO;
import org.springframework.stereotype.Service;

@Service
public interface UserServiceDAO {

    UserDAO registerUser(UserDAO userDAO);
}
