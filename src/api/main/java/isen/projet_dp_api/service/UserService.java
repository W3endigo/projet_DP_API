package isen.projet_dp_api.service;


import isen.projet_dp_api.dao.user.UserServiceDAO;
import isen.projet_dp_api.enums.EmailTypes;
import isen.projet_dp_api.model.dao.UserDAO;
import isen.projet_dp_api.model.dto.UserDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;


import java.util.ArrayList;
import java.util.Optional;

@Log4j2
@Service
public class UserService {

    private final UserServiceDAO userServiceDAO;

    private final TokenService tokenService;

    private final EmailService emailService;

    public UserService(UserServiceDAO userServiceDAO, TokenService tokenService, EmailService emailService) {
        this.userServiceDAO = userServiceDAO;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    public String registerUser(UserDTO userDTO) {
        try {
            userDTO.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));

            var createdUser = userServiceDAO.registerUser(new UserDAO(userDTO));

            var context = new Context();
            context.setVariable("name", createdUser.getFirstName());
            emailService.sendEmailTemplatePicture(createdUser.getEmail(), EmailTypes.REGISTRATION, context, Optional.empty());

            return tokenService.generateToken(new User(createdUser.getEmail(), createdUser.getPassword(), new ArrayList<>()));
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
