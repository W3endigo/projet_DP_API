package isen.projet_dp_api.dao.project.impl;

import isen.projet_dp_api.dao.project.ProjectServiceDAO;
import isen.projet_dp_api.model.dao.ProjectDAO;
import isen.projet_dp_api.utils.TestStrings;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
public class ProjectServiceDAOMock implements ProjectServiceDAO {
    @Override
    public ProjectDAO createProject(ProjectDAO projectDAO) {
        if (projectDAO.getTitle().equals("Project")) {
            return projectDAO;
        }
        throw new IllegalArgumentException("project title is not valid");

    }

    @Override
    public ProjectDAO getProjectById(Integer id) {
        var project = new ProjectDAO();
        project.setId(1);
        project.setTitle(TestStrings.TITLE);
        return project;
    }
}
