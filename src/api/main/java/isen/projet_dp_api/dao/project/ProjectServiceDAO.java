package isen.projet_dp_api.dao.project;


import isen.projet_dp_api.enums.Status;
import isen.projet_dp_api.model.dao.ProjectDAO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ProjectServiceDAO {

    ProjectDAO createProject(ProjectDAO projectDAO);

    ProjectDAO getProjectById(Integer id);

    ProjectDAO updateProject(ProjectDAO projectDAO);

    ProjectDAO getProjectByEmailAndTitle(String email_chef_project, String title);

    void deleteProject(ProjectDAO projectDAO);

    List<ProjectDAO> getProjectByStatus(Status status);
}
