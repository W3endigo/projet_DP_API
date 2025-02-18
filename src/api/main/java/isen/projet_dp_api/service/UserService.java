package isen.projet_dp_api.service;

import isen.projet_dp_api.dao.user.UserServiceDAO;
import isen.projet_dp_api.model.dao.CompanyDAO;
import isen.projet_dp_api.model.dao.UserDAO;
import isen.projet_dp_api.model.dto.EmailDTO;
import isen.projet_dp_api.model.dto.UserDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserServiceDAO userServiceDAO;

    private static final String PASSWORD_UNCHANGED = "********";


    public UserService(UserServiceDAO userServiceDAO){
        this.userServiceDAO = userServiceDAO;
    }

    public UserDTO getUser(EmailDTO emailDTO) {
        var user = userServiceDAO.getUserByEmail(emailDTO.getEmail());
        return new UserDTO(user.getEmail(), null, user.getFirstName(), user.getLastName(), user.getName() == null ? null : user.getName().getName());
    }

    public void updateUser(UserDTO userDTO) {
        var existingUser = userServiceDAO.getUserByEmail(userDTO.getEmail());
        if (!userDTO.getFirstName().isEmpty()) {
            existingUser.setFirstName(userDTO.getFirstName());
        }
        if (!userDTO.getLastName().isEmpty()) {
            existingUser.setLastName(userDTO.getLastName());
        }
        if (!userDTO.getCompany().isEmpty()) {
            existingUser.setName(new CompanyDAO(userDTO.getCompany()));
        }
        if (!PASSWORD_UNCHANGED.equals(userDTO.getPassword())) {
            existingUser.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        }
        userServiceDAO.updateUser(new UserDAO(userDTO));
    }
}
