package isen.projet_dp_api.repository;

import isen.projet_dp_api.model.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDAO, String> {

    UserDAO findByEmail(String email);

}