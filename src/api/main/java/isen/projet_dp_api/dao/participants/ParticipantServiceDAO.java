package isen.projet_dp_api.dao.participants;

import isen.projet_dp_api.model.dao.ParticipantDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ParticipantServiceDAO {

    void createParticipant(ParticipantDAO participantDAO);

    List<ParticipantDAO> getParticipantsByEmail(String email);

    List<ParticipantDAO> getParticipantsByProjectId(Integer projectId);
}
