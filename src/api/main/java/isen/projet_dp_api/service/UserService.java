package isen.projet_dp_api.service;


import isen.projet_dp_api.dao.user.UserServiceDAO;
import isen.projet_dp_api.model.dao.UserDAO;
import isen.projet_dp_api.model.dto.UserDTO;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {

    private final UserServiceDAO userServiceDAO;

    private final TokenService tokenService;

    public UserService(UserServiceDAO userServiceDAO, TokenService tokenService) {
        this.userServiceDAO = userServiceDAO;
        this.tokenService = tokenService;
    }

    public String registerUser(UserDTO userDTO) {

        userDTO.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));

        var createdUser = userServiceDAO.registerUser(new UserDAO(userDTO));

        return tokenService.generateToken(new User(createdUser.getEmail(), createdUser.getPassword(), new ArrayList<>()));
    }
}
