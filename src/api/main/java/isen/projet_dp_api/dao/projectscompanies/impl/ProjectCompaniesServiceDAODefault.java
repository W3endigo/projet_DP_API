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

@Repository
@Profile("!test")
public class ProjectCompaniesServiceDAODefault implements ProjectCompaniesServiceDAO {

    private final ProjectCompaniesRepository projectCompaniesRepository;

    public ProjectCompaniesServiceDAODefault(ProjectCompaniesRepository projectCompaniesRepository) {
        this.projectCompaniesRepository = projectCompaniesRepository;
    }


    @Override
    public ProjectCompaniesDAO createProjectCompanies(ProjectCompaniesDAO projectCompaniesDAO) {
        try {
            projectCompaniesRepository.save(projectCompaniesDAO);
        } catch (JpaObjectRetrievalFailureException e) {
            LogExceptionUtils.logException(this.getClass(), ErrorMessage.ERROR_CREATING_ASSOCIATION_PROJECT_COMPANIES + ErrorMessage.ERROR_FOREIGN_KEY_NOT_FOUND, e, projectCompaniesDAO);
            throw new ApiException(e, ErrorMessage.ERROR_CREATING_ASSOCIATION_PROJECT_COMPANIES, HttpStatus.BAD_REQUEST);
        }
        return projectCompaniesDAO;
    }
}
