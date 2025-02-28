package isen.projet_dp_api.dao.projectscompanies.impl;

import isen.projet_dp_api.dao.projectscompanies.ProjectCompaniesServiceDAO;
import isen.projet_dp_api.model.dao.CompanyDAO;
import isen.projet_dp_api.model.dao.ProjectCompaniesDAO;
import isen.projet_dp_api.model.dao.ProjectDAO;
import isen.projet_dp_api.utils.TestStrings;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("test")
public class ProjectCompaniesServiceDAOMock implements ProjectCompaniesServiceDAO {
    @Override
    public ProjectCompaniesDAO createProjectCompanies(ProjectCompaniesDAO projectCompaniesDAO) {
        if (projectCompaniesDAO.getProject().getId().equals(TestStrings.PROJECT_COMP_ID) && projectCompaniesDAO.getCompany().getName().equals(TestStrings.COMPANY_THIRD)) {
            var projectCompanies = new ProjectCompaniesDAO();
            projectCompanies.setCompany(new CompanyDAO(projectCompaniesDAO.getCompany().getName()));
            projectCompanies.setProject(projectCompaniesDAO.getProject());
            return projectCompanies;
        } else {
            throw new IllegalArgumentException("Association project&company is not valid");
        }
    }


    @Override
    public List<ProjectCompaniesDAO> getProjectCompaniesByProjectId(Integer projectId) {
        if (projectId.equals(1)) {
            var projectCompanies = new ProjectCompaniesDAO();
            var project = new ProjectDAO();
            project.setId(projectId);
            project.setTitle(TestStrings.TITLE);

            projectCompanies.setProject(project);
            projectCompanies.setCompany(new CompanyDAO(TestStrings.COMPANY_THIRD));

            return List.of(projectCompanies);
        } else {
            throw new IllegalArgumentException("Association with this projectId is not valid");
        }
    }

    @Override
    public void deleteProjectCompaniesByCompanyNameAndProjectId(String name, Integer projectId) {
        //TODO
    }

    /*@Override
    public List<ProjectCompaniesDAO> deleteProjectCompanies(ProjectCompaniesDAO projectCompaniesDAO) {
        if (!projectCompaniesDAO.getCompany().getName().equals(TestStrings.COMPANY_THIRD)) {
            throw new IllegalArgumentException("Company name is not valid");
        }
        return null;
    }*/

    @Override
    public List<ProjectCompaniesDAO> getProjectCompaniesByCompanyName(String companyName) {
        if (companyName.equals(TestStrings.COMPANY.replace(" ", "+")) || companyName.equals(TestStrings.COMPANY_THIRD)) {
            return List.of(new ProjectCompaniesDAO(new CompanyDAO(companyName)));
        } else {
            throw new IllegalArgumentException("Company name is not valid");
        }
    }

    @Override
    public List<ProjectCompaniesDAO> getProjectCompaniesByCompanyNameAndProjectId(String companyName, Integer projectId) {
        return List.of();
    }

}
