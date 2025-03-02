package isen.projet_dp_api.service;


import isen.projet_dp_api.dao.company.CompanyServiceDAO;
import isen.projet_dp_api.dao.participants.ParticipantServiceDAO;
import isen.projet_dp_api.dao.project.ProjectServiceDAO;
import isen.projet_dp_api.dao.projectscompanies.ProjectCompaniesServiceDAO;
import isen.projet_dp_api.dao.user.UserServiceDAO;
import isen.projet_dp_api.enums.EmailTypes;
import isen.projet_dp_api.enums.Status;
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

    private final CompanyServiceDAO companyServiceDAO;



    public ProjectService(ProjectServiceDAO projectServiceDAO, ProjectCompaniesServiceDAO projectCompaniesServiceDAO, ParticipantServiceDAO participantServiceDAO, EmailService emailService, UserServiceDAO userServiceDAO, CompanyServiceDAO companyServiceDAO) {
        this.projectServiceDAO = projectServiceDAO;
        this.projectCompaniesServiceDAO = projectCompaniesServiceDAO;
        this.participantServiceDAO = participantServiceDAO;
        this.emailService = emailService;
        this.userServiceDAO = userServiceDAO;
        this.companyServiceDAO = companyServiceDAO;
    }

    @Transactional
    public ProjectCreationRequestResponse createProject(ProjectDTO projectDTO, String email) {
        var user = userServiceDAO.getUserByEmail(email);
        var projectDAO = new ProjectDAO(projectDTO, user);

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

        projectDAO.addParticipant(userServiceDAO.getUserByEmail(email));
        if (projectDTO.getParticipants() != null) {
            for (var participantDTO : projectDTO.getParticipants()) {
                projectDAO.addParticipant(userServiceDAO.getUserByEmail(participantDTO.getEmail()));
            }
        }


        var projectParticipants = projectDTO.getParticipants();

        if (projectParticipants == null) {
            projectParticipants = new ArrayList<>();
        }

        var p_chef = new ParticipantDTO();
        p_chef.setEmail(email);
        projectParticipants.add(p_chef);
        projectDTO.setParticipants(projectParticipants);


        if (projectDTO.getCompagnies() != null) {
            for (var compagnieDTO : projectDTO.getCompagnies()) {
                projectDAO.addCompagnie(companyServiceDAO.getCompanyByName(compagnieDTO.getName()));

            }
        }

        var status = emailError.isPresent() ? ApiResponseMessage.PARTIAL_SUCCESS : ApiResponseMessage.SUCCESS;
        var message = ApiResponseMessage.PROJECT_CREATION_SUCCESS + (emailError.isPresent() ? ApiResponseMessage.EMAIL_SEND_ERROR : ApiResponseMessage.EMAIL_SEND_SUCCESS);

        return new ProjectCreationRequestResponse(status, message, responseDetails, projectDTO);
    }


    @Transactional
    public ProjectDTO updateProject(ProjectDTO projectDTO, String email, String title) {
        var project = projectServiceDAO.getProjectByEmailAndTitle(email, title);

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

        if (projectDTO.getEnd_date() != null) {
            project.setEnd_date(projectDTO.getEnd_date());
        }

        var projectParticipants = projectDTO.getParticipants();

        if (projectParticipants == null) {
            projectParticipants = new ArrayList<>();
        }

        var p_chef = new ParticipantDTO();
        p_chef.setEmail(projectDTO.getEmail_chef_project());
        projectParticipants.add(p_chef);
        projectDTO.setParticipants(projectParticipants);

        if (projectDTO.getParticipants() != null) {
            var currentParticipants = project.getParticipants();

            var newParticipantsEmails = projectDTO.getParticipants().stream().map(ParticipantDTO::getEmail).toList();

            currentParticipants.removeIf(participant -> {
                if (!newParticipantsEmails.contains(participant.getUser().getEmail())) {
                    participantServiceDAO.deleteParticipantByUserEmailAndProjectId(participant.getUser().getEmail(), participant.getProject().getId());
                    return true;
                }
                return false;
            });

            for (var participantDTO : projectDTO.getParticipants()) {
                boolean exists = currentParticipants.stream()
                        .anyMatch(p -> p.getUser().getEmail().equals(participantDTO.getEmail()));

                if (!exists) {
                    project.addParticipant(userServiceDAO.getUserByEmail(participantDTO.getEmail()));
                }
            }
        }


        if (projectDTO.getCompagnies() != null) {
            var currentCompanies = project.getCompanies();
            var newCompanyNames = projectDTO.getCompagnies().stream().map(ProjectCompaniesDTO::getName).toList();
            currentCompanies.removeIf(company -> {
                if (!newCompanyNames.contains(company.getCompany().getName())) {
                    projectCompaniesServiceDAO.deleteProjectCompaniesByCompanyNameAndProjectId(
                            company.getCompany().getName(), project.getId()
                    );
                    return true;
                }
                return false;
            });

            for (var companyDTO : projectDTO.getCompagnies()) {
                var existingCompany = projectCompaniesServiceDAO.getProjectCompaniesByCompanyNameAndProjectId(companyDTO.getName(), project.getId());
                if (existingCompany.isEmpty()) {
                    project.addCompagnie(companyServiceDAO.getCompanyByName(companyDTO.getName()));
                }
            }
        }
        projectServiceDAO.updateProject(project);

        return new ProjectDTO(
                project.getEmail().getEmail(),
                project.getDescription(),
                project.getTitle(),
                project.getStatus(),
                project.getStart_date(),
                project.getEnd_date(),
                project.getParticipants().stream().map(p -> new ParticipantDTO(p.getUser().getEmail())).toList(),
                project.getCompanies().stream().map(c -> new ProjectCompaniesDTO(c.getCompany().getName())).toList()
        );


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

    @Transactional
    public void deleteProject(String email, String title) {
        var projectDAO = projectServiceDAO.getProjectByEmailAndTitle(email, title);
        projectServiceDAO.deleteProject(projectDAO);
    }


    public List<ProjectDTO> getProjectsByStatus(Status status) {
        var projectsDAO = projectServiceDAO.getProjectByStatus(status);
        var projectsDTO = new ArrayList<ProjectDTO>();
        for (var project : projectsDAO) {

            var projectParticipantDAO = project.getParticipants();
            var participantsDTO = new ArrayList<ParticipantDTO>();
            if (projectParticipantDAO != null) {
                for (var participant : projectParticipantDAO) {
                    participantsDTO.add(new ParticipantDTO(participant.getUser().getEmail()));
                }
            }

            var projectCompaniesDAO = project.getCompanies();
            var companiesDTO = new ArrayList<ProjectCompaniesDTO>();
            if (projectCompaniesDAO != null) {
                for (var companie : projectCompaniesDAO) {
                    companiesDTO.add(new ProjectCompaniesDTO(companie.getCompany().getName()));
                }
            }

            projectsDTO.add(new ProjectDTO(project.getEmail().getEmail(), project.getDescription(), project.getTitle(), project.getStatus(), project.getStart_date(), project.getEnd_date(), participantsDTO, companiesDTO));
        }
        log.info(projectsDTO);
        return projectsDTO;
    }



}

