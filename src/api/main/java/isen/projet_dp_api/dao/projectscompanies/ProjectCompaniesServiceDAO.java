package isen.projet_dp_api.dao.projectscompanies;

import isen.projet_dp_api.model.dao.ProjectCompaniesDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectCompaniesServiceDAO {

    ProjectCompaniesDAO createProjectCompanies(ProjectCompaniesDAO projectCompaniesDAO);

    List<ProjectCompaniesDAO> getProjectCompaniesByProjectId(Integer projectId);

    void deleteProjectCompanies(ProjectCompaniesDAO projectCompaniesDAO);

    List<ProjectCompaniesDAO> getProjectCompaniesDAOSByCompany_Name(String companyName);
}
