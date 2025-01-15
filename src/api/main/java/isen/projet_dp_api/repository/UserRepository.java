package isen.projet_dp_api.repository;

import isen.projet_dp_api.model.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDAO, String> {
}