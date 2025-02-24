package isen.projet_dp_api.dao.company.impl;

import isen.projet_dp_api.dao.company.CompanyServiceDAO;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.dao.CompanyDAO;
import isen.projet_dp_api.repository.CompanyRepository;
import isen.projet_dp_api.utils.exception.ErrorMessage;
import isen.projet_dp_api.utils.exception.LogExceptionUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!test")
public class CompanyServiceDAODefault implements CompanyServiceDAO {

    private final CompanyRepository companyRepository;

    public CompanyServiceDAODefault(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public void registerCompany(CompanyDAO companyDAO) {
        if (companyRepository.existsById(companyDAO.getName())) {
            LogExceptionUtils.logException(this.getClass(), String.format(ErrorMessage.ERROR_COMPANY_ALREADY_EXIST,  companyDAO.getName()), null,  companyDAO.getName());
            throw new ApiException(String.format(ErrorMessage.ERROR_COMPANY_ALREADY_EXIST, companyDAO.getName()), HttpStatus.CONFLICT);
        }
        try {
            companyRepository.save(companyDAO);
        } catch (JpaObjectRetrievalFailureException e) {
            LogExceptionUtils.logException(this.getClass(), ErrorMessage.ERROR_COMPANY_ALREADY_EXIST + ErrorMessage.ERROR_FOREIGN_KEY_NOT_FOUND, e, companyDAO);
            throw new ApiException(e, ErrorMessage.ERROR_COMPANY_ALREADY_EXIST + String.format(ErrorMessage.ERROR_COMPANY_NOT_FOUND, companyDAO.getName()), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public CompanyDAO getCompanyByName(String name) {
        return this.companyRepository.findById(name).orElse(null);
    }
}
