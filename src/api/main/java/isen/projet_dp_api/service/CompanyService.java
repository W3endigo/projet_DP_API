package isen.projet_dp_api.service;

import isen.projet_dp_api.dao.company.CompanyServiceDAO;
import isen.projet_dp_api.dao.projectscompanies.ProjectCompaniesServiceDAO;
import isen.projet_dp_api.model.dao.CompanyDAO;
import isen.projet_dp_api.model.dao.ProjectCompaniesDAO;
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
        var company = companyServiceDAO.getCompanyByName(name);
        return new CompanyDTO(company.getName());

    public List<CompanyDTO> getAllCompanies() {
        var companiesDAO = companyServiceDAO.getAllCompanies();
        return companiesDAO.stream()
                .map(company -> new CompanyDTO(company.getName()))
                .toList();
    }

    @Transactional
    public void deleteCompanyByName(String name) {
        var company = companyServiceDAO.getCompanyByName(name);

        var projectCompanies = projectCompaniesServiceDAO.getProjectCompaniesDAOSByCompany_Name(company.getName());

        for (ProjectCompaniesDAO projectCompaniesDAO : projectCompanies) {
            projectCompaniesServiceDAO.deleteProjectCompanies(projectCompaniesDAO);
        }

}
