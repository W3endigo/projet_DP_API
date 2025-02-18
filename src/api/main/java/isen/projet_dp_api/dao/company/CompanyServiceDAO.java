package isen.projet_dp_api.dao.company;

import isen.projet_dp_api.model.dao.CompanyDAO;
import org.springframework.stereotype.Service;

@Service
public interface CompanyServiceDAO {
    void registerCompany(CompanyDAO companyDAO);

    CompanyDAO getCompanyByName(String name);
}
