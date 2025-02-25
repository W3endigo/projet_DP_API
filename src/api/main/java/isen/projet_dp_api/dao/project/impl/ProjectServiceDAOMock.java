package isen.projet_dp_api.dao.project.impl;

import isen.projet_dp_api.dao.project.ProjectServiceDAO;
import isen.projet_dp_api.model.dao.ProjectDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
public class ProjectServiceDAOMock implements ProjectServiceDAO {
    @Override
    public ProjectDAO createProject(ProjectDAO projectDAO) {
        //TODO
        return null;
    }
}
