package isen.projet_dp_api.dao.projectscompanies.impl;

import isen.projet_dp_api.dao.projectscompanies.ProjectCompaniesServiceDAO;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.dao.ProjectCompaniesDAO;
import isen.projet_dp_api.repository.ProjectCompaniesRepository;
import isen.projet_dp_api.utils.exception.ErrorMessage;
import isen.projet_dp_api.utils.exception.LogExceptionUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("!test")
public class ProjectCompaniesServiceDAODefault implements ProjectCompaniesServiceDAO {

    private final ProjectCompaniesRepository projectCompaniesRepository;

    public ProjectCompaniesServiceDAODefault(ProjectCompaniesRepository projectCompaniesRepository) {
        this.projectCompaniesRepository = projectCompaniesRepository;
    }


    @Override
    public ProjectCompaniesDAO createProjectCompanies(ProjectCompaniesDAO projectCompaniesDAO) {
        if (projectCompaniesRepository.existsById(projectCompaniesDAO.getId())) {
            LogExceptionUtils.logException(this.getClass(),
                    String.format(ErrorMessage.ERROR_COMPANY_ALREADY_IN_PROJECT, projectCompaniesDAO.getId()),
                    null, projectCompaniesDAO.getId());
        }
        try {
            projectCompaniesRepository.save(projectCompaniesDAO);
        } catch (JpaObjectRetrievalFailureException e) {
            LogExceptionUtils.logException(this.getClass(), ErrorMessage.ERROR_CREATING_ASSOCIATION_PROJECT_COMPANIES + ErrorMessage.ERROR_COMPANY_NOT_FOUND, e, projectCompaniesDAO);
            throw new ApiException(e, ErrorMessage.ERROR_CREATING_ASSOCIATION_PROJECT_COMPANIES, HttpStatus.BAD_REQUEST);
        }
        return projectCompaniesDAO;
    }

    @Override
    public List<ProjectCompaniesDAO> getProjectCompaniesByProjectId(Integer projectId) {
        return this.projectCompaniesRepository.findByProjectId(projectId);
    }

    @Override
    public void deleteProjectCompanies(ProjectCompaniesDAO projectCompaniesDAO) {
        if (projectCompaniesRepository.existsById(projectCompaniesDAO.getId())) {
            LogExceptionUtils.logException(this.getClass(),
                    String.format(ErrorMessage.ERROR_ASSOCIATION_NOT_FOUND, projectCompaniesDAO.getId()),
                    null, projectCompaniesDAO.getId());
        } try {
            this.projectCompaniesRepository.delete(projectCompaniesDAO);
        }
        catch (JpaObjectRetrievalFailureException e) {
            LogExceptionUtils.logException(this.getClass(), ErrorMessage.ERROR_ASSOCIATION_NOT_FOUND + ErrorMessage.ERROR_COMPANY_NOT_FOUND, e, projectCompaniesDAO);
            throw new ApiException(e, ErrorMessage.ERROR_ASSOCIATION_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<ProjectCompaniesDAO> getProjectCompaniesByCompanyName(String companyName) {
        return this.projectCompaniesRepository.findByCompanyName(companyName);
    }

}
