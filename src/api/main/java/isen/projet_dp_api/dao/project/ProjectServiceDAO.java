package isen.projet_dp_api.dao.project;


import isen.projet_dp_api.model.dao.ProjectDAO;
import org.springframework.stereotype.Service;

@Service
public interface ProjectServiceDAO {

    ProjectDAO createProject(ProjectDAO projectDAO);
}
