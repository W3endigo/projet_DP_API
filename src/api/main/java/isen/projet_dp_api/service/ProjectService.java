package isen.projet_dp_api.service;


import isen.projet_dp_api.dao.participants.ParticipantServiceDAO;
import isen.projet_dp_api.dao.project.ProjectServiceDAO;
import isen.projet_dp_api.dao.projectscompanies.ProjectCompaniesServiceDAO;
import isen.projet_dp_api.enums.EmailTypes;
import isen.projet_dp_api.model.ProjectCreationRequestResponse;
import isen.projet_dp_api.model.dao.*;
import isen.projet_dp_api.model.dto.ParticipantDTO;
import isen.projet_dp_api.model.dto.ProjectCompaniesDTO;
import isen.projet_dp_api.model.dto.ProjectDTO;
import isen.projet_dp_api.utils.ApiResponseMessage;
import isen.projet_dp_api.utils.ApiStrings;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
public class ProjectService {

    private final ProjectServiceDAO projectServiceDAO;

    private final ProjectCompaniesServiceDAO projectCompaniesServiceDAO;

    private final ParticipantServiceDAO participantServiceDAO;

    private final EmailService emailService;


    public ProjectService(ProjectServiceDAO projectServiceDAO, ProjectCompaniesServiceDAO projectCompaniesServiceDAO, ParticipantServiceDAO participantServiceDAO, EmailService emailService) {
        this.projectServiceDAO = projectServiceDAO;
        this.projectCompaniesServiceDAO = projectCompaniesServiceDAO;
        this.participantServiceDAO = participantServiceDAO;
        this.emailService = emailService;
    }

    public void addProjectCompanies(ProjectDTO projectDTO, ProjectDAO projectDAO) {
        for (var compagnieDTO : projectDTO.getCompagnies()) {
            ProjectCompaniesId projectCompaniesId = new ProjectCompaniesId();
            projectCompaniesId.setProjectId(projectDAO.getId());
            projectCompaniesId.setName(compagnieDTO.getName());

            var compagnieDAO = new ProjectCompaniesDAO();
            compagnieDAO.setId(projectCompaniesId);
            compagnieDAO.setProject(projectDAO);
            compagnieDAO.setCompany(new CompanyDAO(compagnieDTO.getName()));

            this.projectCompaniesServiceDAO.createProjectCompanies(compagnieDAO);
        }
    }

    public void addParticipant(String email, ProjectDAO projectDAO) {
        ParticipantId participantId = new ParticipantId();
        participantId.setEmail(email);
        participantId.setProjectId(projectDAO.getId());

        ParticipantDAO participantDAO = new ParticipantDAO();
        participantDAO.setId(participantId);
        participantDAO.setUser(new UserDAO(email));
        participantDAO.setProject(projectDAO);

        participantServiceDAO.createParticipant(participantDAO);
    }

    @Transactional
    public ProjectCreationRequestResponse createProject(ProjectDTO projectDTO, String email) {

        var projectDAO = new ProjectDAO(projectDTO, email);

        projectDAO = projectServiceDAO.createProject(projectDAO);

        var emailError = prepareSendCreationProjectEmail(projectDAO);
        var responseDetails = emailError.map(error -> Map.of(
                ApiResponseMessage.PROJECT_CREATION, ApiResponseMessage.FAILURE,
                ApiResponseMessage.EMAIL_SENDING, ApiResponseMessage.FAILURE,
                ApiResponseMessage.EMAIL_ERROR, error
        )).orElseGet(() -> Map.of(
                ApiResponseMessage.PROJECT_CREATION, ApiResponseMessage.SUCCESS,
                ApiResponseMessage.EMAIL_SENDING, ApiResponseMessage.SUCCESS
        ));

        addParticipant(email, projectDAO);

        if (projectDTO.getParticipants() != null) {
            for (var participantDTO : projectDTO.getParticipants()) {
                this.addParticipant(participantDTO.getEmail(), projectDAO);
            }
        }

        if (projectDTO.getCompagnies() != null) {
            this.addProjectCompanies(projectDTO, projectDAO);
        }

        var status = emailError.isPresent() ? ApiResponseMessage.PARTIAL_SUCCESS : ApiResponseMessage.SUCCESS;
        var message = ApiResponseMessage.PROJECT_CREATION_SUCCESS + (emailError.isPresent() ? ApiResponseMessage.EMAIL_SEND_ERROR : ApiResponseMessage.EMAIL_SEND_SUCCESS);

        return new ProjectCreationRequestResponse(status, message, responseDetails, projectDTO);
    }

    private Optional<String> prepareSendCreationProjectEmail(ProjectDAO createdProject) {
        var context = new Context();
        context.setVariable(ApiStrings.NAME, createdProject.getEmail());
        return emailService.sendEmailTemplatePicture(createdProject.getEmail().getEmail(), EmailTypes.PROJECTCREATION, context, Optional.empty());
    }

    public ProjectDTO getProjectDTO(Integer projectId) {

        ArrayList<ParticipantDTO> participantDTO = new ArrayList<>();
        ArrayList<ProjectCompaniesDTO> projectCompaniesDTO = new ArrayList<>();

        var projectDAO = projectServiceDAO.getProjectById(projectId);

        var participantsDAO = participantServiceDAO.getParticipantsByProject_Id(projectDAO.getId());
        var companiesDAO = projectCompaniesServiceDAO.getProjectCompaniesDAOSByProject_Id(projectDAO.getId());

        for (ParticipantDAO participant : participantsDAO) {
            participantDTO.add(new ParticipantDTO(participant.getUser().getEmail()));
        }

        for (ProjectCompaniesDAO projectCompaniesDAO : companiesDAO) {
            projectCompaniesDTO.add(new ProjectCompaniesDTO(projectCompaniesDAO.getCompany().getName()));
        }

        return new ProjectDTO(null, projectDAO.getTitle(), projectDAO.getDescription(), null,  projectDAO.getStart_date(), null, participantDTO, projectCompaniesDTO);

    }


}

