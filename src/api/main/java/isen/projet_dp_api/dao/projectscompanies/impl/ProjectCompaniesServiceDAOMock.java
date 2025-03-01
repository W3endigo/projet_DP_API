package isen.projet_dp_api.dao.projectscompanies.impl;

import isen.projet_dp_api.dao.projectscompanies.ProjectCompaniesServiceDAO;
import isen.projet_dp_api.model.dao.CompanyDAO;
import isen.projet_dp_api.model.dao.ProjectCompaniesDAO;
import isen.projet_dp_api.model.dao.ProjectCompaniesId;
import isen.projet_dp_api.model.dao.ProjectDAO;
import isen.projet_dp_api.utils.TestStrings;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Log4j2
@Repository
@Profile("test")
public class ProjectCompaniesServiceDAOMock implements ProjectCompaniesServiceDAO {

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
        if (!name.equals(TestStrings.COMPANY_THIRD) && !name.equals(TestStrings.COMPANY_FOURTH)) {
            throw new IllegalArgumentException("Company name is not valid");
        }
    }

    @Override
    public List<ProjectCompaniesDAO> getProjectCompaniesByCompanyName(String companyName) {
        if (companyName.equals(TestStrings.COMPANY.replace(" ", "+")) || companyName.equals(TestStrings.COMPANY_THIRD) || companyName.equals(TestStrings.COMPANY_FOURTH) || companyName.equals(TestStrings.COMPANY_FIFTH)) {
            var pc = new ProjectCompaniesDAO(new CompanyDAO(companyName));
            var project = new ProjectDAO();
            project.setId(1);
            project.setTitle(TestStrings.TITLE);
            pc.setProject(project);

            var projectCompaniesId = new ProjectCompaniesId();
            projectCompaniesId.setProjectId(project.getId());
            projectCompaniesId.setName(companyName);

            pc.setId(projectCompaniesId);
            return List.of(pc);
        } else {
            throw new IllegalArgumentException("Company name is not valid");
        }
    }

    @Override
    public List<ProjectCompaniesDAO> getProjectCompaniesByCompanyNameAndProjectId(String companyName, Integer projectId) {
        if ((companyName.equals(TestStrings.COMPANY_THIRD) || companyName.equals(TestStrings.COMPANY_SIXTH)) && projectId.equals(TestStrings.PROJECT_COMP_ID)) {
            return List.of(new ProjectCompaniesDAO(new CompanyDAO(companyName)));
        } else {
            throw new IllegalArgumentException("Company name is not valid");
        }
    }

}
