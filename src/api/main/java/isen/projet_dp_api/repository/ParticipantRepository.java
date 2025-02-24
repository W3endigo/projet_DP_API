package isen.projet_dp_api.repository;

import isen.projet_dp_api.model.dao.ParticipantDAO;
import isen.projet_dp_api.model.dao.ParticipantId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<ParticipantDAO, ParticipantId> {

    boolean existsById(ParticipantId id);
}
