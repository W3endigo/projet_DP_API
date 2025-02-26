package isen.projet_dp_api.service;


import isen.projet_dp_api.dao.participants.ParticipantServiceDAO;
import isen.projet_dp_api.dao.project.ProjectServiceDAO;
import isen.projet_dp_api.dao.projectscompanies.ProjectCompaniesServiceDAO;
import isen.projet_dp_api.dao.user.UserServiceDAO;
import isen.projet_dp_api.enums.EmailTypes;
import isen.projet_dp_api.model.ProjectCreationRequestResponse;
import isen.projet_dp_api.model.dao.*;
import isen.projet_dp_api.model.dto.CompanyDTO;
import isen.projet_dp_api.model.dto.ParticipantDTO;
import isen.projet_dp_api.model.dto.ProjectCompaniesDTO;
import isen.projet_dp_api.model.dto.ProjectDTO;
import isen.projet_dp_api.utils.ApiResponseMessage;
import isen.projet_dp_api.utils.ApiStrings;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Service
public class ProjectService {

    private final ProjectServiceDAO projectServiceDAO;

    private final ProjectCompaniesServiceDAO projectCompaniesServiceDAO;

    private final ParticipantServiceDAO participantServiceDAO;

    private final EmailService emailService;
    private final UserServiceDAO userServiceDAO;


    public ProjectService(ProjectServiceDAO projectServiceDAO, ProjectCompaniesServiceDAO projectCompaniesServiceDAO, ParticipantServiceDAO participantServiceDAO, EmailService emailService, UserServiceDAO userServiceDAO) {
        this.projectServiceDAO = projectServiceDAO;
        this.projectCompaniesServiceDAO = projectCompaniesServiceDAO;
        this.participantServiceDAO = participantServiceDAO;
        this.emailService = emailService;
        this.userServiceDAO = userServiceDAO;
    }

    public ProjectCompaniesDAO createProjectCompanies(String name, ProjectDAO projectDAO) {
        ProjectCompaniesId projectCompaniesId = new ProjectCompaniesId();
        projectCompaniesId.setProjectId(projectDAO.getId());
        projectCompaniesId.setName(name);

        var compagnieDAO = new ProjectCompaniesDAO();
        compagnieDAO.setId(projectCompaniesId);
        compagnieDAO.setProject(projectDAO);
        compagnieDAO.setCompany(new CompanyDAO(name));

        this.projectCompaniesServiceDAO.createProjectCompanies(compagnieDAO);

        return compagnieDAO;
    }

    public ParticipantDAO createParticipant(String email, ProjectDAO projectDAO) {
        var participantId = new ParticipantId();
        participantId.setEmail(email);
        participantId.setProjectId(projectDAO.getId());

        var participantDAO = new ParticipantDAO();
        participantDAO.setId(participantId);
        participantDAO.setUser(new UserDAO(email));
        participantDAO.setProject(projectDAO);

        participantServiceDAO.createParticipant(participantDAO);

        return participantDAO;
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

        createParticipant(email, projectDAO);

        if (projectDTO.getParticipants() != null) {
            for (var participantDTO : projectDTO.getParticipants()) {
                this.createParticipant(participantDTO.getEmail(), projectDAO);
            }
        }

        if (projectDTO.getCompagnies() != null) {
            for (var compagnieDTO : projectDTO.getCompagnies()) {
                this.createProjectCompanies(compagnieDTO.getName(), projectDAO);
            }
        }

        var status = emailError.isPresent() ? ApiResponseMessage.PARTIAL_SUCCESS : ApiResponseMessage.SUCCESS;
        var message = ApiResponseMessage.PROJECT_CREATION_SUCCESS + (emailError.isPresent() ? ApiResponseMessage.EMAIL_SEND_ERROR : ApiResponseMessage.EMAIL_SEND_SUCCESS);

        return new ProjectCreationRequestResponse(status, message, responseDetails, projectDTO);
    }

    @Transactional
    public ProjectDTO updateProject(ProjectDTO projectDTO, String email, String title) {


        var project = projectServiceDAO.getProjectByEmailAndTitle(email, title);
        var listParticipants = new ArrayList<ParticipantDTO>();
        var listCompagnies = new ArrayList<ProjectCompaniesDTO>();

        if (projectDTO.getTitle() != null && !projectDTO.getTitle().isEmpty()) {
            project.setTitle(projectDTO.getTitle());
        }

        if (projectDTO.getEmail_chef_project() != null && !projectDTO.getEmail_chef_project().isEmpty()) {
            var existingUserFromMail = userServiceDAO.getUserByEmail(projectDTO.getEmail_chef_project());
            project.setEmail(existingUserFromMail);
        }

        if (projectDTO.getDescription() != null && !projectDTO.getDescription().isEmpty()) {
            project.setDescription(projectDTO.getDescription());
        }

        if (projectDTO.getStatus() != null) {
            project.setStatus(projectDTO.getStatus());
        }

        if (projectDTO.getStart_date() != null) {
            project.setStart_date(projectDTO.getStart_date());
        }

        if (projectDTO.getStart_date() != null) {
            project.setEnd_date(projectDTO.getEnd_date());
        }

        for (var participant : project.getParticipants()) {
            listParticipants.add(new ParticipantDTO(participant.getUser().getEmail()));
        }

        for (var company : project.getCompanies()) {
            listCompagnies.add(new ProjectCompaniesDTO(company.getCompany().getName()));
        }

        var projectDAO = projectServiceDAO.updateProject(project);


        if (projectDTO.getParticipants() != null) {
            var participants = participantServiceDAO.getParticipantsByProjectId(projectDAO.getId());
            var participantDTONames = projectDTO.getParticipants().stream()
                    .map(ParticipantDTO::getEmail)
                    .toList();
            for (var participant : participants) {
                var participantDAOEmail = participant.getUser().getEmail();

                if (!participantDTONames.contains(participantDAOEmail)) {
                    participantServiceDAO.deleteParticipant(participant);
                }
            }

            for (var participantDTOName : participantDTONames) {
                boolean exists = participants.stream()
                        .anyMatch(p -> p.getUser().getEmail().equals(participantDTOName));

                if (!exists) {
                    var createdParticipant = createParticipant(participantDTOName, projectDAO);
                    listParticipants.add(new ParticipantDTO(createdParticipant.getUser().getEmail()));
                }
            }
        }
        var projectCompanies = projectCompaniesServiceDAO.getProjectCompaniesByProjectId(projectDAO.getId());
        log.info("VOILA {}", projectCompanies);
        projectCompaniesServiceDAO.deleteProjectCompanies(projectCompanies.getFirst());
        var projectCompaniesNew = projectCompaniesServiceDAO.getProjectCompaniesByProjectId(projectDAO.getId());
        log.info("VOILA {}", projectCompaniesNew);


        /*if (projectDTO.getCompagnies() != null) {
            var projectCompanies = projectCompaniesServiceDAO.getProjectCompaniesByProjectId(projectDAO.getId());
            var projectCompanyNames = projectDTO.getCompagnies().stream()
                    .map(ProjectCompaniesDTO::getName)
                    .toList();

            for (var projectCompany : projectCompanies) {
                var companyName = projectCompany.getCompany().getName();

                if (!projectCompanyNames.contains(companyName)) {
                    projectCompaniesServiceDAO.deleteProjectCompanies(projectCompany);
                }
            }

            for (var projectCompanyName : projectCompanyNames) {
                boolean exists = projectCompanies.stream()
                        .anyMatch(pc -> pc.getCompany().getName().equals(projectCompanyName));

                if (!exists) {
                    var createdProjectCompanies = createProjectCompanies(projectCompanyName, projectDAO);
                    listCompagnies.add(new ProjectCompaniesDTO(createdProjectCompanies.getCompany().getName()));
                }
            }
        }*/

        return new ProjectDTO(project.getEmail().getEmail(), project.getDescription(), project.getTitle(), project.getStatus(), project.getStart_date(), project.getEnd_date(), listParticipants, listCompagnies);

    }

    private Optional<String> prepareSendCreationProjectEmail(ProjectDAO createdProject) {
        var context = new Context();
        context.setVariable(ApiStrings.NAME, createdProject.getEmail());
        return emailService.sendEmailTemplatePicture(createdProject.getEmail().getEmail(), EmailTypes.PROJECTCREATION, context, Optional.empty());
    }

    public ProjectDTO getProjectById(Integer projectId) {

        var participantDTO = new ArrayList<ParticipantDTO>();
        var projectCompaniesDTO = new ArrayList<ProjectCompaniesDTO>();

        var projectDAO = projectServiceDAO.getProjectById(projectId);

        var participantsDAO = participantServiceDAO.getParticipantsByProjectId(projectId);
        var companiesDAO = projectCompaniesServiceDAO.getProjectCompaniesByProjectId(projectId);

        for (var participant : participantsDAO) {
            participantDTO.add(new ParticipantDTO(participant.getUser().getEmail()));
        }

        for (var projectCompaniesDAO : companiesDAO) {
            projectCompaniesDTO.add(new ProjectCompaniesDTO(projectCompaniesDAO.getCompany().getName()));
        }

        return new ProjectDTO(projectDAO.getEmail().getEmail(), projectDAO.getDescription(), projectDAO.getTitle(), projectDAO.getStatus(),  projectDAO.getStart_date(), projectDAO.getEnd_date(), participantDTO, projectCompaniesDTO);

    }


}

