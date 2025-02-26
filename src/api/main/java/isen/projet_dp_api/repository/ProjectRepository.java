package isen.projet_dp_api.repository;

import isen.projet_dp_api.model.dao.ProjectDAO;
import isen.projet_dp_api.model.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectDAO, Integer> {

    boolean existsByEmailAndTitle(UserDAO email_chef_project, String title);

    Optional<ProjectDAO> findByEmailEmailAndTitle(String email_chef_project, String title);

    boolean existsByTitle(String title);
}
