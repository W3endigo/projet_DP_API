package isen.projet_dp_api.dao.company.impl;

import isen.projet_dp_api.dao.company.CompanyServiceDAO;
import isen.projet_dp_api.model.dao.CompanyDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Profile("test")
public class CompanyServiceDAOMock implements CompanyServiceDAO {

    @Override
    public CompanyDAO registerCompany(CompanyDAO companyDAO) {
        if (companyDAO.getName().equals(TestStrings.COMPANY)) {
            return companyDAO;
        } else {
            throw new IllegalArgumentException("Company name is not valid");
        }
    }

    @Override
    public CompanyDAO getCompanyByName(String name) {
        if (name.equals(TestStrings.COMPANY.replace(" ", "+"))) {
            return new CompanyDAO(TestStrings.COMPANY);
        } else {
            throw new IllegalArgumentException("Company name is not valid");
        }
    }

    @Override
    public List<CompanyDAO> getAllCompanies() {
        //TODO
        return null;
    }

    @Override
    public void deleteCompany(CompanyDAO companyDAO) {
        //TODO
    }

}
