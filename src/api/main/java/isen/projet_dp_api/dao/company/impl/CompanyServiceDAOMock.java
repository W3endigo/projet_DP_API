package isen.projet_dp_api.dao.company.impl;

import isen.projet_dp_api.dao.company.CompanyServiceDAO;
import isen.projet_dp_api.model.dao.CompanyDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
public class CompanyServiceDAOMock implements CompanyServiceDAO {

    @Override
    public void registerCompany(CompanyDAO companyDAO) {
        // TODO
    }

}
