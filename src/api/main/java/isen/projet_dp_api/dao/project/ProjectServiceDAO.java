package isen.projet_dp_api.dao.project;


import isen.projet_dp_api.model.dao.ProjectDAO;
import isen.projet_dp_api.model.dto.ProjectDTO;
import org.springframework.stereotype.Service;

@Service
public interface ProjectServiceDAO {

    ProjectDAO createProject(ProjectDTO projectDTO, String email);
}
