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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Profile("!test")
public class ProjectCompaniesServiceDAODefault implements ProjectCompaniesServiceDAO {

    private final ProjectCompaniesRepository projectCompaniesRepository;

    public ProjectCompaniesServiceDAODefault(ProjectCompaniesRepository projectCompaniesRepository) {
        this.projectCompaniesRepository = projectCompaniesRepository;
    }


    @Override
    public List<ProjectCompaniesDAO> getProjectCompaniesByProjectId(Integer projectId) {
        return this.projectCompaniesRepository.findByProjectId(projectId);
    }

    @Override
    @Transactional
    public void deleteProjectCompaniesByCompanyNameAndProjectId(String companyName, Integer projectId) {
        var projectCompanies = projectCompaniesRepository.findByCompanyNameAndProjectId(companyName, projectId);

        if (projectCompanies != null && !projectCompanies.isEmpty()) {
            projectCompaniesRepository.deleteAll(projectCompanies);
        }
    }

    @Override
    public List<ProjectCompaniesDAO> getProjectCompaniesByCompanyName(String companyName) {
        if (!projectCompaniesRepository.existsByCompanyName(companyName)) {
            LogExceptionUtils.logException(this.getClass(),
                    String.format(ErrorMessage.ERROR_COMPANY_NOT_FOUND, companyName),
                    null, companyName);
        } try {
            return this.projectCompaniesRepository.findByCompanyName(companyName);
        }
        catch (JpaObjectRetrievalFailureException e) {
            LogExceptionUtils.logException(this.getClass(), ErrorMessage.ERROR_ASSOCIATION_NOT_FOUND + ErrorMessage.ERROR_COMPANY_NOT_FOUND, e, companyName);
            throw new ApiException(e, ErrorMessage.ERROR_ASSOCIATION_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<ProjectCompaniesDAO> getProjectCompaniesByCompanyNameAndProjectId(String companyName, Integer projectId) {
        try {
            return this.projectCompaniesRepository.findByCompanyNameAndProjectId(companyName, projectId);
        } catch (JpaObjectRetrievalFailureException e) {
            LogExceptionUtils.logException(this.getClass(), ErrorMessage.ERROR_ASSOCIATION_NOT_FOUND + ErrorMessage.ERROR_COMPANY_NOT_FOUND, e, companyName);
            throw new ApiException(e, ErrorMessage.ERROR_ASSOCIATION_NOT_FOUND, HttpStatus.BAD_REQUEST);
        }
    }

}
