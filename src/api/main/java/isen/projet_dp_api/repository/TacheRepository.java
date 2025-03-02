package isen.projet_dp_api.repository;

import isen.projet_dp_api.model.dao.TacheDAO;
import isen.projet_dp_api.model.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TacheRepository extends JpaRepository<TacheDAO, Integer> {

    Optional<List<TacheDAO>>  findByProjectTitle(String title);

    Optional<TacheDAO> findByNameAndAssignedUserEmail(String title, String assignedUserEmail);
}
