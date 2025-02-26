package isen.projet_dp_api.dao.participants.impl;

import isen.projet_dp_api.dao.participants.ParticipantServiceDAO;
import isen.projet_dp_api.model.dao.ParticipantDAO;
import isen.projet_dp_api.model.dao.ProjectDAO;
import isen.projet_dp_api.model.dao.UserDAO;
import isen.projet_dp_api.utils.TestStrings;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Profile;


import java.util.List;

@Repository
@Profile("test")
public class ParticipantServiceDAOMock implements ParticipantServiceDAO {
    @Override
    public void createParticipant(ParticipantDAO participantDAO) {
        if (!participantDAO.getUser().getEmail().equals(TestStrings.EMAIL_HAROLD) && !participantDAO.getProject().getEmail().getEmail().equals(TestStrings.EMAIL_HAROLD)) {
            throw new IllegalArgumentException("Poject participant is not valid");
        }
    }

    @Override
    public List<ParticipantDAO> getParticipantsByEmail(String email) {
        if (email.equals(TestStrings.EMAIL_HAROLD)) {
            var part = new ParticipantDAO();
            part.setUser(new UserDAO(TestStrings.EMAIL_HAROLD));
            var project = new ProjectDAO();
            project.setId(1);
            project.setTitle(TestStrings.TITLE);
            part.setProject(project);
            return List.of(part);
        } else {
            throw new IllegalArgumentException("Email is not valid");

        }
    }

    @Override
    public List<ParticipantDAO> getParticipantsByProjectId(Integer projectId) {
        if (projectId.equals(1)) {
            var part = new ParticipantDAO();
            part.setUser(new UserDAO(TestStrings.EMAIL_HAROLD));
            var project = new ProjectDAO();
            project.setId(1);
            project.setTitle(TestStrings.TITLE);
            part.setProject(project);
            return List.of(part);
        } else {
            throw new IllegalArgumentException("Poject id is not valid");
        }
    }

    @Override
    public void deleteParticipant(ParticipantDAO participantDAO) {
        if (!participantDAO.getUser().getEmail().equals(TestStrings.EMAIL_HAROLD)) {
            throw new IllegalArgumentException("Participant name is not valid");
        }
    }
}
