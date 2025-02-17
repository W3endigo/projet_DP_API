package isen.projet_dp_api.service;

import isen.projet_dp_api.dao.company.CompanyServiceDAO;
import isen.projet_dp_api.model.dao.CompanyDAO;
import isen.projet_dp_api.model.dto.CompanyDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Map;

@Log4j2
@Service
public class CompanyService {

    private final CompanyServiceDAO companyServiceDAO;

    public CompanyService(CompanyServiceDAO companyServiceDAO) {
        this.companyServiceDAO = companyServiceDAO;
    }

    public void registerCompany(CompanyDTO companyDTO) {
        companyServiceDAO.registerCompany(new CompanyDAO(companyDTO));
    }
}
