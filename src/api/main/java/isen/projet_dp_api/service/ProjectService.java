package isen.projet_dp_api.service;


import isen.projet_dp_api.dao.participants.ParticipantServiceDAO;
import isen.projet_dp_api.dao.project.ProjectServiceDAO;
import isen.projet_dp_api.dao.projectscompanies.ProjectCompaniesServiceDAO;
import isen.projet_dp_api.model.dao.*;
import isen.projet_dp_api.model.dto.ProjectDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class ProjectService {

    private final ProjectServiceDAO projectServiceDAO;

    private final ProjectCompaniesServiceDAO projectCompaniesServiceDAO;

    private final ParticipantServiceDAO participantServiceDAO;


    public ProjectService(ProjectServiceDAO projectServiceDAO, ProjectCompaniesServiceDAO projectCompaniesServiceDAO, ParticipantServiceDAO participantServiceDAO) {
        this.projectServiceDAO = projectServiceDAO;
        this.projectCompaniesServiceDAO = projectCompaniesServiceDAO;
        this.participantServiceDAO = participantServiceDAO;
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
    public void createProject(ProjectDTO projectDTO, String email) {


        var projectDAO = new ProjectDAO(projectDTO, email);

        projectDAO = projectServiceDAO.createProject(projectDAO);

        addParticipant(email, projectDAO);

        if (projectDTO.getParticipants() != null) {
            for (var participantDTO : projectDTO.getParticipants()) {
                this.addParticipant(participantDTO.getEmail(), projectDAO);
            }
        }

        if (projectDTO.getCompagnies() != null) {
            this.addProjectCompanies(projectDTO, projectDAO);
            }
        }


}

