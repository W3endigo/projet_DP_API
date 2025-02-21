package isen.projet_dp_api.repository;

import isen.projet_dp_api.model.dao.ProjectDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<ProjectDAO, Integer> {
}
