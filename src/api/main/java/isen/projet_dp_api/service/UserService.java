package isen.projet_dp_api.service;

import isen.projet_dp_api.dao.participants.ParticipantServiceDAO;
import isen.projet_dp_api.dao.user.UserServiceDAO;
import isen.projet_dp_api.enums.EmailTypes;
import isen.projet_dp_api.model.UpdateUserRequestResponse;
import isen.projet_dp_api.model.dao.*;
import isen.projet_dp_api.model.dto.ProjectDTO;
import isen.projet_dp_api.model.dto.UserDTO;
import isen.projet_dp_api.utils.ApiResponseMessage;
import isen.projet_dp_api.utils.ApiStrings;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
public class UserService {

    private final UserServiceDAO userServiceDAO;

    private final EmailService emailService;

    private final ParticipantServiceDAO participantServiceDAO;


    private final ProjectService projectService;

    public UserService(UserServiceDAO userServiceDAO, EmailService emailService, ParticipantServiceDAO participantServiceDAO,  ProjectService projectService) {
        this.userServiceDAO = userServiceDAO;
        this.emailService = emailService;
        this.participantServiceDAO = participantServiceDAO;
        this.projectService = projectService;
    }

    public UserDTO getUser(String email) {
        var user = userServiceDAO.getUserByEmail(email);
        return new UserDTO(null, user.getFirstName(), user.getLastName(), user.getName() == null ? null : user.getName().getName());
    }

    public ArrayList<ProjectDTO> getProjectParticipation(UserDetails userDetails) {

        var projectsDTO = new ArrayList<ProjectDTO>();

        var participations = this.participantServiceDAO.getParticipantsByEmail(userDetails.getUsername());
        for (var participation : participations) {
            projectsDTO.add(projectService.getProjectById(participation.getProject().getId()));
        }
        return projectsDTO;
    }

    public UpdateUserRequestResponse updateUser(UserDTO userDTO, String email) {
        var existingUser = userServiceDAO.getUserByEmail(email);
        if (userDTO.getFirstName() != null && !userDTO.getFirstName().isEmpty()) {
            existingUser.setFirstName(userDTO.getFirstName());
        }
        if (userDTO.getLastName() != null && !userDTO.getLastName().isEmpty()) {
            existingUser.setLastName(userDTO.getLastName());
        }
        if (userDTO.getCompany() != null && !userDTO.getCompany().isEmpty()) {
            existingUser.setName(new CompanyDAO(userDTO.getCompany()));
        }
        if (userDTO.getPassword() != null) {
            existingUser.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
        }
        var updatedUser = userServiceDAO.updateUser(existingUser);
        var emailError = prepareSendUpdateEmail(updatedUser);
        var responseDetails = emailError.map(error -> Map.of(
                ApiResponseMessage.USER_UPDATE, ApiResponseMessage.SUCCESS,
                ApiResponseMessage.EMAIL_SENDING, ApiResponseMessage.FAILURE,
                ApiResponseMessage.EMAIL_ERROR, error
        )).orElseGet(() -> Map.of(
                ApiResponseMessage.USER_UPDATE, ApiResponseMessage.SUCCESS,
                ApiResponseMessage.EMAIL_SENDING, ApiResponseMessage.SUCCESS
        ));

        var status = emailError.isPresent() ? ApiResponseMessage.PARTIAL_SUCCESS : ApiResponseMessage.SUCCESS;
        var message = ApiResponseMessage.UPDATE_USER_SUCCESS + (emailError.isPresent() ? ApiResponseMessage.EMAIL_SEND_ERROR : ApiResponseMessage.EMAIL_SEND_SUCCESS);

        return new UpdateUserRequestResponse(status, message, responseDetails, new UserDTO(null, updatedUser.getFirstName(), updatedUser.getLastName(), updatedUser.getName().getName()));
    }

    private Optional<String> prepareSendUpdateEmail(UserDAO createdUser) {
        var context = new Context();
        context.setVariable(ApiStrings.NAME, createdUser.getFirstName());
        return emailService.sendEmailTemplatePicture(createdUser.getEmail(), EmailTypes.UPDATE_PROFILE, context, Optional.empty());
    }

}
