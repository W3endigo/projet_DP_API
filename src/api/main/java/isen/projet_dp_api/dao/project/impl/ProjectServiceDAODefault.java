package isen.projet_dp_api.dao.project.impl;


import isen.projet_dp_api.dao.project.ProjectServiceDAO;
import isen.projet_dp_api.enums.Status;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.dao.ProjectDAO;
import isen.projet_dp_api.repository.ProjectRepository;
import isen.projet_dp_api.utils.exception.ErrorMessage;
import isen.projet_dp_api.utils.exception.LogExceptionUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@Profile("!test")
public class ProjectServiceDAODefault implements ProjectServiceDAO {

    private final ProjectRepository projectRepository;

    public ProjectServiceDAODefault(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectDAO createProject(ProjectDAO projectDAO) {
        if (projectRepository.existsByEmailAndTitle(projectDAO.getEmail(), projectDAO.getTitle())) {
            LogExceptionUtils.logException(this.getClass(),
                    String.format(ErrorMessage.ERROR_PROJECT_ALREADY_EXIST, projectDAO.getTitle()), null, projectDAO);
            throw new ApiException(String.format(ErrorMessage.ERROR_PROJECT_ALREADY_EXIST, projectDAO.getTitle()), HttpStatus.CONFLICT);
        }
        try {
            return projectRepository.save(projectDAO);
        } catch (JpaObjectRetrievalFailureException e) {
            LogExceptionUtils.logException(this.getClass(), ErrorMessage.ERROR_CREATING_PROJECT + ErrorMessage.ERROR_FOREIGN_KEY_NOT_FOUND, e, projectDAO);
            throw new ApiException(e, ErrorMessage.ERROR_CREATING_PROJECT, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ProjectDAO getProjectById(Integer id) {
        return projectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Project not found with id: " + id));
    }

    @Override
    public ProjectDAO updateProject(ProjectDAO projectDAO) {
        if (projectRepository.existsByEmailEmailAndTitleAndIdNot(projectDAO.getEmail().getEmail(), projectDAO.getTitle(), projectDAO.getId())) {
            throw new ApiException(String.format(ErrorMessage.ERROR_PROJECT_ALREADY_EXIST, projectDAO.getTitle()), HttpStatus.CONFLICT);
        }
        try {
            return projectRepository.save(projectDAO);
        } catch(JpaObjectRetrievalFailureException e) {
            LogExceptionUtils.logException(this.getClass(), ErrorMessage.ERROR_UPDATING_PROJECT + ErrorMessage.ERROR_FOREIGN_KEY_NOT_FOUND, e, projectDAO);
            throw new ApiException(e, ErrorMessage.ERROR_UPDATING_PROJECT, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ProjectDAO getProjectByEmailAndTitle(String email_chef_project, String title) {
        return projectRepository.findByEmailEmailAndTitle(email_chef_project, title).orElseThrow(() -> new EntityNotFoundException(String.format("Project not found with title: " + title + ", and email: " + email_chef_project)));
    }

    @Override
    public void deleteProject(ProjectDAO projectDAO) {
        projectRepository.delete(projectDAO);
    }

    @Override
    public List<ProjectDAO> getProjectByStatus(Status status) {
        return projectRepository.findByStatus(status).orElseThrow(() -> new EntityNotFoundException("Project not found with status: " + status));
    }
}
