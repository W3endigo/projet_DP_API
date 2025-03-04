package isen.projet_dp_api.dao.project.impl;

import isen.projet_dp_api.dao.project.ProjectServiceDAO;
import isen.projet_dp_api.model.dao.ProjectDAO;
import isen.projet_dp_api.model.dao.UserDAO;
import isen.projet_dp_api.utils.TestStrings;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Log4j2
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
        if (id.equals(1)) {
            var project = new ProjectDAO();
            project.setId(1);
            project.setTitle(TestStrings.TITLE);
            project.setEmail(new UserDAO(TestStrings.EMAIL_HAROLD));
            return project;
        }
        if (id.equals(2)) {
            var project = new ProjectDAO();
            project.setId(2);
            project.setTitle(TestStrings.TITLE);
            project.setEmail(new UserDAO(TestStrings.EMAIL_HAROLD));
            return project;
        }
        else {
            var project = new ProjectDAO();
            project.setId(3);
            project.setTitle(TestStrings.TITLE);
            project.setEmail(new UserDAO(TestStrings.EMAIL_HAROLD));
            return project;
        }
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
        if ((title.equals(TestStrings.TITLE) || title.equals(TestStrings.TITLE_SECOND)) && (email_chef_project.equals(TestStrings.EMAIL_HAROLD) || email_chef_project.equals(TestStrings.EMAIL_ASTRID))) {
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
            throw new IllegalArgumentException("project doesn't exist");
        }
    }
}
