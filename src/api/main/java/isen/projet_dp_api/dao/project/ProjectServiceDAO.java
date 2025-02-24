package isen.projet_dp_api.dao.project;


import isen.projet_dp_api.model.dao.ProjectDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectServiceDAO {

    ProjectDAO createProject(ProjectDAO projectDAO);

    ProjectDAO getProjectById(Integer id);
}
