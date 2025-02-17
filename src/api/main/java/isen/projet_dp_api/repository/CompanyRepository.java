package isen.projet_dp_api.repository;

import isen.projet_dp_api.model.dao.CompanyDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<CompanyDAO, String> {
}
