package isen.projet_dp_api.dao.project.impl;


import isen.projet_dp_api.dao.project.ProjectServiceDAO;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.dao.ProjectDAO;
import isen.projet_dp_api.repository.ProjectRepository;
import isen.projet_dp_api.utils.exception.ErrorMessage;
import isen.projet_dp_api.utils.exception.LogExceptionUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!test")
public class ProjectServiceDAODefault implements ProjectServiceDAO {

    private final ProjectRepository projectRepository;

    public ProjectServiceDAODefault(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectDAO createProject(ProjectDAO projectDAO) {
        if (projectRepository.existsByTitleAndEmail_chef_project(projectDAO.getTitle(), projectDAO.getEmail_chef_project().getEmail())) {
            LogExceptionUtils.logException(this.getClass(),
                    String.format(ErrorMessage.ERROR_PROJECT_ALREADY_EXIST, projectDAO.getTitle()), null, projectDAO);
            throw new ApiException(String.format(ErrorMessage.ERROR_PROJECT_ALReADY_EXIST, projectDAO.getTitle()), HttpStatus.CONFLICT);
        }
        try {
            return projectRepository.save(projectDAO);
        } catch (JpaObjectRetrievalFailureException e) {
            LogExceptionUtils.logException(this.getClass(), ErrorMessage.ERROR_CREATING_PROJECT + ErrorMessage.ERROR_FOREIGN_KEY_NOT_FOUND, e, projectDAO);
            throw new ApiException(e, ErrorMessage.ERROR_CREATING_PROJECT, HttpStatus.BAD_REQUEST);
        }
    }
}
