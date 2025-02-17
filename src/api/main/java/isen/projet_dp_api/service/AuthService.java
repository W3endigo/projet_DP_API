package isen.projet_dp_api.service;


import isen.projet_dp_api.dao.user.UserServiceDAO;
import isen.projet_dp_api.enums.EmailTypes;
import isen.projet_dp_api.model.dao.UserDAO;
import isen.projet_dp_api.model.RegisterRequestResponse;
import isen.projet_dp_api.model.dto.UserDTO;
import isen.projet_dp_api.utils.ApiResponseMessage;
import isen.projet_dp_api.utils.ApiStrings;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;


import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
public class AuthService {

    private final UserServiceDAO userServiceDAO;

    private final TokenService tokenService;

    private final EmailService emailService;

    public AuthService(UserServiceDAO userServiceDAO, TokenService tokenService, EmailService emailService) {
        this.userServiceDAO = userServiceDAO;
        this.tokenService = tokenService;
        this.emailService = emailService;
    }

    public RegisterRequestResponse registerUser(UserDTO userDTO) {
        userDTO.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        var createdUser = userServiceDAO.registerUser(new UserDAO(userDTO));
        var token = tokenService.generateToken(new User(createdUser.getEmail(), createdUser.getPassword(), new ArrayList<>()));

        var emailError = prepareSendRegistrationEmail(createdUser);
        var responseDetails = emailError.map(error -> Map.of(
                ApiResponseMessage.USER_REGISTRATION, ApiResponseMessage.SUCCESS,
                ApiResponseMessage.EMAIL_SENDING, ApiResponseMessage.FAILURE,
                ApiResponseMessage.EMAIL_ERROR, error
        )).orElseGet(() -> Map.of(
                ApiResponseMessage.USER_REGISTRATION, ApiResponseMessage.SUCCESS,
                ApiResponseMessage.EMAIL_SENDING, ApiResponseMessage.SUCCESS
        ));

        var status = emailError.isPresent() ? ApiResponseMessage.PARTIAL_SUCCESS : ApiResponseMessage.SUCCESS;
        var message = ApiResponseMessage.REGISTER_USER_SUCCESS + (emailError.isPresent() ? ApiResponseMessage.EMAIL_SEND_ERROR : ApiResponseMessage.EMAIL_SEND_SUCCESS);

        return new RegisterRequestResponse(status, message, responseDetails, token);
    }

    private Optional<String> prepareSendRegistrationEmail(UserDAO createdUser) {
        var context = new Context();
        context.setVariable(ApiStrings.NAME, createdUser.getFirstName());
        return emailService.sendEmailTemplatePicture(createdUser.getEmail(), EmailTypes.REGISTRATION, context, Optional.empty());
    }
}
