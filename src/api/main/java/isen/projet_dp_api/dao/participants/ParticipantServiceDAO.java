package isen.projet_dp_api.dao.participants;

import isen.projet_dp_api.model.dao.ParticipantDAO;
import org.springframework.stereotype.Service;

@Service
public interface ParticipantServiceDAO {

    ParticipantDAO createParticipant(ParticipantDAO participantDAO);

}
