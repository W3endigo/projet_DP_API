package isen.projet_dp_api.dao.projectscompanies;

import isen.projet_dp_api.model.dao.ProjectCompaniesDAO;
import org.springframework.stereotype.Service;

@Service
public interface ProjectCompaniesServiceDAO {

    ProjectCompaniesDAO createProjectCompanies(ProjectCompaniesDAO projectCompaniesDAO);
}
