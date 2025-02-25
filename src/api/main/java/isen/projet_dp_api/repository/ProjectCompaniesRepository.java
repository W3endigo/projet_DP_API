package isen.projet_dp_api.repository;

import isen.projet_dp_api.model.dao.ProjectCompaniesDAO;
import isen.projet_dp_api.model.dao.ProjectCompaniesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectCompaniesRepository extends JpaRepository<ProjectCompaniesDAO, ProjectCompaniesId> {

    boolean existsById(ProjectCompaniesId projectCompaniesId);


    List<ProjectCompaniesDAO> findByProjectId(Integer projectId);

    List<ProjectCompaniesDAO> findByCompanyName(String companyName);
}
