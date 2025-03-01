package isen.projet_dp_api.dao.participants.impl;

import isen.projet_dp_api.dao.participants.ParticipantServiceDAO;
import isen.projet_dp_api.model.dao.ParticipantDAO;
import isen.projet_dp_api.repository.ParticipantRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Repository
@Profile("!test")
public class ParticipantServiceDAODefault implements ParticipantServiceDAO {

    private final ParticipantRepository participantRepository;

    public ParticipantServiceDAODefault(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }



    @Override
    public List<ParticipantDAO> getParticipantsByEmail(String email) {
        return participantRepository.getParticipantByUserEmail(email);
    }

    @Override
    public List<ParticipantDAO> getParticipantsByProjectId(Integer projectId) {
        return participantRepository.getParticipantByProjectId(projectId);
    }


    @Override
    @Transactional
    public void deleteParticipantByUserEmailAndProjectId(String name, Integer projectId) {
        List<ParticipantDAO> participants = participantRepository.findByUserEmailAndProjectId(name, projectId);
        if (participants != null && !participants.isEmpty()) {
            participantRepository.deleteAll(participants);
        }
    }

}
