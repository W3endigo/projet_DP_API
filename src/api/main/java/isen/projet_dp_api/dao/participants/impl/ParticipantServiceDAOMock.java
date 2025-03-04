package isen.projet_dp_api.dao.participants.impl;

import isen.projet_dp_api.dao.participants.ParticipantServiceDAO;
import isen.projet_dp_api.model.dao.ParticipantDAO;
import isen.projet_dp_api.model.dao.ParticipantId;
import isen.projet_dp_api.model.dao.ProjectDAO;
import isen.projet_dp_api.model.dao.UserDAO;
import isen.projet_dp_api.utils.TestStrings;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;
import org.springframework.context.annotation.Profile;


import java.util.List;

@Log4j2
@Repository
@Profile("test")
public class ParticipantServiceDAOMock implements ParticipantServiceDAO {


    @Override
    public List<ParticipantDAO> getParticipantsByEmail(String email) {
        if (email.equals(TestStrings.EMAIL_HAROLD) ) {
            var part = new ParticipantDAO();
            part.setUser(new UserDAO(email));
            var project = new ProjectDAO();
            project.setId(1);
            project.setTitle(TestStrings.TITLE);
            part.setProject(project);

            var participantId = new ParticipantId();
            participantId.setEmail(email);
            participantId.setProjectId(project.getId());

            part.setId(participantId);

            return List.of(part);
        } if (email.equals(TestStrings.EMAIL_DRAGON)) {
            var part = new ParticipantDAO();
            part.setUser(new UserDAO(email));
            var project = new ProjectDAO();
            project.setId(2);
            project.setTitle(TestStrings.TITLE);
            part.setProject(project);

            var participantId = new ParticipantId();
            participantId.setEmail(email);
            participantId.setProjectId(project.getId());

            part.setId(participantId);

            return List.of(part);
        }
        if (email.equals(TestStrings.EMAIL_BIDULE)) {
            var part = new ParticipantDAO();
            part.setUser(new UserDAO(email));
            var project = new ProjectDAO();
            project.setId(3);
            project.setTitle(TestStrings.TITLE);
            part.setProject(project);

            var participantId = new ParticipantId();
            participantId.setEmail(email);
            participantId.setProjectId(project.getId());

            part.setId(participantId);
            return List.of(part);

        }
        else {
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
        }
        if (projectId.equals(2)) {
            var part = new ParticipantDAO();
            part.setUser(new UserDAO(TestStrings.EMAIL_DRAGON));
            var project = new ProjectDAO();
            project.setId(2);
            project.setTitle(TestStrings.TITLE);
            part.setProject(project);
            return List.of(part);
        }
        else {
            throw new IllegalArgumentException("Poject id is not valid");
        }
    }


    @Override
    public void deleteParticipantByUserEmailAndProjectId(String email, Integer projectId) {
    }
}
