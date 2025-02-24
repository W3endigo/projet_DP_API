package isen.projet_dp_api.repository;

import isen.projet_dp_api.model.dao.ProjectCompaniesDAO;
import isen.projet_dp_api.model.dao.ProjectCompaniesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectCompaniesRepository extends JpaRepository<ProjectCompaniesDAO, ProjectCompaniesId> {
    @Override
    boolean existsById(ProjectCompaniesId projectCompaniesId);
}
