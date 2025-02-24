package isen.projet_dp_api.dao.participants.impl;

import isen.projet_dp_api.dao.participants.ParticipantServiceDAO;
import isen.projet_dp_api.model.dao.ParticipantDAO;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("test")
public class ParticipantServiceDAOMock implements ParticipantServiceDAO {
    @Override
    public void createParticipant(ParticipantDAO participantDAO) {
        //TODO
    }
}
