package isen.projet_dp_api.dao.participants.impl;

import isen.projet_dp_api.dao.participants.ParticipantServiceDAO;
import isen.projet_dp_api.model.dao.ParticipantDAO;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Profile;


import java.util.List;

@Repository
@Profile("test")
public class ParticipantServiceDAOMock implements ParticipantServiceDAO {
    @Override
    public void createParticipant(ParticipantDAO participantDAO) {
        //TODO
    }

    @Override
    public List<ParticipantDAO> getParticipantsByEmail(String email) {
        //TODO
        return null;
    }

    @Override
    public List<ParticipantDAO> getParticipantsByProject_Id(Integer projectId) {
        //TODO
        return null;
    }
}
