package isen.projet_dp_api.service;


import isen.projet_dp_api.dao.participants.ParticipantServiceDAO;
import isen.projet_dp_api.dao.project.ProjectServiceDAO;
import isen.projet_dp_api.dao.projectscompanies.ProjectCompaniesServiceDAO;
import isen.projet_dp_api.model.dao.*;
import isen.projet_dp_api.model.dto.ProjectDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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

    public void createProject(ProjectDTO projectDTO, String email) {

        var projectDAO = new ProjectDAO(projectDTO, email);

        // D'abord, on sauvegarde le projet pour obtenir un ID
        projectDAO = projectServiceDAO.createProject(projectDAO);

        if (projectDTO.getParticipants() != null) {
            for (var participantDTO : projectDTO.getParticipants()) {
                ParticipantId participantId = new ParticipantId();
                participantId.setEmail(participantDTO.getEmail());
                participantId.setProjectId(projectDAO.getId()); // Assurez-vous que projectDAO a bien un ID

                ParticipantDAO participantDAO = new ParticipantDAO();
                participantDAO.setId(participantId);
                participantDAO.setUser(new UserDAO(participantDTO.getEmail()));
                participantDAO.setProject(projectDAO); // 🔥 Ajout important : association du projet

                // On sauvegarde maintenant le participant
                participantServiceDAO.createParticipant(participantDAO);
            }
        }

        if (projectDTO.getCompagnies() != null) {
            for (var compagnieDTO : projectDTO.getCompagnies()) {
                ProjectCompaniesId projectCompaniesId = new ProjectCompaniesId();
                projectCompaniesId.setProjectId(projectDAO.getId()); // 🔥 Assurez-vous que projectDAO a un ID
                projectCompaniesId.setName(compagnieDTO.getName());

                var compagnieDAO = new ProjectCompaniesDAO();
                compagnieDAO.setId(projectCompaniesId);
                compagnieDAO.setProject(projectDAO);
                compagnieDAO.setCompany(new CompanyDAO(compagnieDTO.getName()));

                compagnieDAO = projectCompaniesServiceDAO.createProjectCompanies(compagnieDAO);
                projectDAO.addCompagnie(compagnieDAO);
            }
        }
    }

}

