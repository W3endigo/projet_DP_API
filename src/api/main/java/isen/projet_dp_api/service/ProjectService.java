package isen.projet_dp_api.service;


import isen.projet_dp_api.dao.project.ProjectServiceDAO;
import isen.projet_dp_api.model.dao.ProjectDAO;
import isen.projet_dp_api.model.dto.ProjectDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ProjectService {

    private final ProjectServiceDAO projectServiceDAO;


    public ProjectService(ProjectServiceDAO projectServiceDAO) {
        this.projectServiceDAO = projectServiceDAO;
    }

    public ProjectDAO createProject(ProjectDTO projectDTO, String email) {
        return projectServiceDAO.createProject(projectDTO, email);
    }

}

