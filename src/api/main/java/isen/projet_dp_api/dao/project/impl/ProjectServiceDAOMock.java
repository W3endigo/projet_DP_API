package isen.projet_dp_api.dao.project.impl;

import isen.projet_dp_api.dao.project.ProjectServiceDAO;
import isen.projet_dp_api.model.dao.ProjectDAO;
import isen.projet_dp_api.model.dao.UserDAO;
import isen.projet_dp_api.utils.TestStrings;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
public class ProjectServiceDAOMock implements ProjectServiceDAO {
    @Override
    public ProjectDAO createProject(ProjectDAO projectDAO) {
        if (projectDAO.getTitle().equals("Project")) {
            projectDAO.setId(1);
            return projectDAO;
        }
        throw new IllegalArgumentException("project title is not valid");

    }

    @Override
    public ProjectDAO getProjectById(Integer id) {
        var project = new ProjectDAO();
        project.setId(1);
        project.setTitle(TestStrings.TITLE);
        project.setEmail(new UserDAO(TestStrings.EMAIL_HAROLD));
        return project;
    }

    @Override
    public ProjectDAO updateProject(ProjectDAO projectDAO) {
        var project = new ProjectDAO();
        project.setId(projectDAO.getId());
        project.setTitle(projectDAO.getTitle());
        return project;
    }

    @Override
    public ProjectDAO getProjectByEmailAndTitle(String email_chef_project, String title) {
        if (title.equals(TestStrings.TITLE) && email_chef_project.equals(TestStrings.EMAIL_HAROLD)) {
            var project = new ProjectDAO();
            project.setId(1);
            project.setTitle(title);
            project.setEmail(new UserDAO(email_chef_project));
            return project;
        }
        throw new IllegalArgumentException("project title or email is not valid");
    }

    @Override
    public void deleteProject(ProjectDAO projectDAO) {
        if (!projectDAO.getTitle().equals(TestStrings.TITLE) && !projectDAO.getEmail().getEmail().equals(TestStrings.EMAIL_HAROLD)) {
            throw new IllegalArgumentException("project title or email is not valid");
        }
    }
}
