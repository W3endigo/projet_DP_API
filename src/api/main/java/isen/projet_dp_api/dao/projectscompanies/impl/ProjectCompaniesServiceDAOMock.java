package isen.projet_dp_api.dao.projectscompanies.impl;

import isen.projet_dp_api.dao.projectscompanies.ProjectCompaniesServiceDAO;
import isen.projet_dp_api.model.dao.ProjectCompaniesDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("test")
public class ProjectCompaniesServiceDAOMock implements ProjectCompaniesServiceDAO {
    @Override
    public ProjectCompaniesDAO createProjectCompanies(ProjectCompaniesDAO projectCompaniesDAO) {
        //TODO
        return null;
    }


    @Override
    public List<ProjectCompaniesDAO> getProjectCompaniesByProjectId(Integer projectId) {
        //TODO
        return null;
    }

    @Override
    public void deleteProjectCompanies(ProjectCompaniesDAO projectCompaniesDAO) {
        //TODO
    }

    @Override
    public List<ProjectCompaniesDAO> getProjectCompaniesByCompanyName(String companyName) {
        //TODO
        return null;
    }

}
