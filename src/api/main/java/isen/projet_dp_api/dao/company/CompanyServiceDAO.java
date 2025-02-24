package isen.projet_dp_api.dao.company;

import isen.projet_dp_api.model.dao.CompanyDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompanyServiceDAO {

    CompanyDAO registerCompany(CompanyDAO companyDAO);

    CompanyDAO getCompanyByName(String name);

    List<CompanyDAO> getAllCompanies();
}
