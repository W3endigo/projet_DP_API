package isen.projet_dp_api.dao.project.impl;


import isen.projet_dp_api.dao.project.ProjectServiceDAO;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.dao.ProjectDAO;
import isen.projet_dp_api.model.dto.ProjectDTO;
import isen.projet_dp_api.repository.ProjectRepository;
import isen.projet_dp_api.repository.UserRepository;
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
    public ProjectDAO createProject(ProjectDTO projectDTO, String email) {
        try {
            return projectRepository.save(new ProjectDAO(projectDTO, email));
        } catch (JpaObjectRetrievalFailureException e) {
            LogExceptionUtils.logException(this.getClass(), ErrorMessage.ERROR_CREATING_PROJECT + ErrorMessage.ERROR_FOREIGN_KEY_NOT_FOUND, e, projectDTO);
            throw new ApiException(e, ErrorMessage.ERROR_CREATING_PROJECT, HttpStatus.BAD_REQUEST);
        }
    }
}
