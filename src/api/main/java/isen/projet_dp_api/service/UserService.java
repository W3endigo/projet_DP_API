package isen.projet_dp_api.service;


import isen.projet_dp_api.dao.user.UserServiceDAO;
import isen.projet_dp_api.enums.EmailTypes;
import isen.projet_dp_api.model.dao.UserDAO;
import isen.projet_dp_api.model.RequestResponse;
import isen.projet_dp_api.model.dto.UserDTO;
import isen.projet_dp_api.utils.ApiResponseMessage;
import isen.projet_dp_api.utils.exception.ErrorMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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

    public Map<String, Object> registerUser(UserDTO userDTO) {
        userDTO.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        var createdUser = userServiceDAO.registerUser(new UserDAO(userDTO));

        var responseData = new HashMap<String, Object>();

        var context = new Context();
        context.setVariable("name", createdUser.getFirstName());

        var emailError = emailService.sendEmailTemplatePicture(createdUser.getEmail(), EmailTypes.REGISTRATION, context, Optional.empty());
        if (emailError.isPresent()) {
            responseData.put("requestStatus", new RequestResponse(
                    ApiResponseMessage.PARTIAL_SUCCESS,
                    ApiResponseMessage.REGISTER_USER_SUCCESS + ApiResponseMessage.EMAIL_SEND_ERROR,
                    Map.of(
                            ApiResponseMessage.USER_REGISTRATION, ApiResponseMessage.SUCCESS,
                            ApiResponseMessage.EMAIL_SENDING, ApiResponseMessage.FAILURE,
                            ApiResponseMessage.EMAIL_ERROR, emailError.get()
                    )
            ));
        } else {
            responseData.put("requestStatus", new RequestResponse(
                    ApiResponseMessage.SUCCESS,
                    ApiResponseMessage.REGISTER_USER_SUCCESS + ApiResponseMessage.EMAIL_SEND_SUCCESS,
                    Map.of(
                            ApiResponseMessage.USER_REGISTRATION, ApiResponseMessage.SUCCESS,
                            ApiResponseMessage.EMAIL_SENDING, ApiResponseMessage.SUCCESS
                    )
            ));
        }

        responseData.put("token", tokenService.generateToken(new User(createdUser.getEmail(), createdUser.getPassword(), new ArrayList<>())));
        return responseData;
    }
}
