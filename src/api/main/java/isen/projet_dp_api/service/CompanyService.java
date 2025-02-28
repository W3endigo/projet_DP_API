package isen.projet_dp_api.service;

import isen.projet_dp_api.dao.company.CompanyServiceDAO;
import isen.projet_dp_api.dao.projectscompanies.ProjectCompaniesServiceDAO;
import isen.projet_dp_api.model.dao.CompanyDAO;
import isen.projet_dp_api.model.dto.CompanyDTO;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;


@Log4j2
@Service
public class CompanyService {

    private final CompanyServiceDAO companyServiceDAO;

    private final ProjectCompaniesServiceDAO projectCompaniesServiceDAO;

    public CompanyService(CompanyServiceDAO companyServiceDAO, ProjectCompaniesServiceDAO projectCompaniesServiceDAO) {
        this.companyServiceDAO = companyServiceDAO;
        this.projectCompaniesServiceDAO = projectCompaniesServiceDAO;
    }

    public CompanyDTO registerCompany(CompanyDTO companyDTO) {
        return new CompanyDTO(companyServiceDAO.registerCompany(new CompanyDAO(companyDTO)).getName());

    }

    public CompanyDTO getCompanyByName(String name) {
        return new CompanyDTO(companyServiceDAO.getCompanyByName(name).getName());
    }

    public List<CompanyDTO> getAllCompanies() {
        return companyServiceDAO.getAllCompanies().stream()
                .map(company -> new CompanyDTO(company.getName()))
                .toList();
    }

    @Transactional
    public void deleteCompanyByName(String name) {
        var projectCompanies = projectCompaniesServiceDAO.getProjectCompaniesByCompanyName(companyServiceDAO.getCompanyByName(name).getName());

        for (var projectCompaniesDAO : projectCompanies) {
            projectCompaniesServiceDAO.deleteProjectCompaniesByCompanyNameAndProjectId(projectCompaniesDAO.getCompany().getName(), projectCompaniesDAO.getProject().getId());
        }

        companyServiceDAO.deleteCompany(companyServiceDAO.getCompanyByName(name));
    }


}
