package isen.projet_dp_api.dao.participants.impl;

import isen.projet_dp_api.dao.participants.ParticipantServiceDAO;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.dao.ParticipantDAO;
import isen.projet_dp_api.repository.ParticipantRepository;
import isen.projet_dp_api.utils.exception.ErrorMessage;
import isen.projet_dp_api.utils.exception.LogExceptionUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!test")
public class ParticipantServiceDAODefault implements ParticipantServiceDAO {

    private final ParticipantRepository participantRepository;

    public ParticipantServiceDAODefault(ParticipantRepository participantRepository) {
        this.participantRepository = participantRepository;
    }

    @Override
    public void createParticipant(ParticipantDAO participantDAO) {
        try {
            participantRepository.save(participantDAO);
        } catch (JpaObjectRetrievalFailureException e) {
            LogExceptionUtils.logException(this.getClass(), ErrorMessage.ERROR_CREATING_PARTICIPANT + ErrorMessage.ERROR_FOREIGN_KEY_NOT_FOUND, e, participantDAO);
            throw new ApiException(e, ErrorMessage.ERROR_CREATING_PARTICIPANT, HttpStatus.BAD_REQUEST);
        }
    }
}
