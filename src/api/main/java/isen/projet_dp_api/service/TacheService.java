package isen.projet_dp_api.service;

import isen.projet_dp_api.dao.project.ProjectServiceDAO;
import isen.projet_dp_api.dao.tache.TacheServiceDAO;
import isen.projet_dp_api.dao.user.UserServiceDAO;
import isen.projet_dp_api.model.dao.TacheDAO;
import isen.projet_dp_api.model.dto.TacheDTO;
import isen.projet_dp_api.utils.ApiStrings;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class TacheService {

    private final TacheServiceDAO tacheServiceDAO;
    private final ProjectServiceDAO projectServiceDAO;
    private final UserServiceDAO userServiceDAO;

    public TacheService(TacheServiceDAO tacheServiceDAO, ProjectServiceDAO projectServiceDAO, UserServiceDAO userServiceDAO) {
        this.tacheServiceDAO = tacheServiceDAO;
        this.projectServiceDAO = projectServiceDAO;
        this.userServiceDAO = userServiceDAO;
    }

    @Transactional
    public TacheDTO createTask(String title, TacheDTO tacheDTO, String username) {
        log.debug(ApiStrings.CREATING_TASK, tacheDTO.getName(), title);
        var project = projectServiceDAO.getProjectByEmailAndTitle(username, title);
        TacheDAO tacheDAO = new TacheDAO();
        tacheDAO.setName(tacheDTO.getName());
        tacheDAO.setProject(project);
        tacheDAO.setDescription(tacheDTO.getDescription());
        if (tacheDTO.getAssignedUser() != null) {
            tacheDAO.setAssignedUser(userServiceDAO.getUserByEmail(tacheDTO.getAssignedUser()));
        } else {
            tacheDAO.setAssignedUser(userServiceDAO.getUserByEmail(username));
        }

        TacheDAO createdTask = tacheServiceDAO.createTask(tacheDAO);
        log.info(createdTask);

        return new TacheDTO(
                createdTask.getName(),
                createdTask.getDescription(),
                createdTask.getProject().getId(),
                createdTask.getAssignedUser() != null ? createdTask.getAssignedUser().getEmail() : null
        );
    }

    @Transactional
    public TacheDTO updateTask(String title, TacheDTO tacheDTO, String username) {
        log.debug(ApiStrings.UPDATING_TASK, tacheDTO.getName(), title);
        TacheDAO tacheDAO = tacheServiceDAO.getTaskByNameAndAssignedUserEmail(title, username);
                tacheDAO.setName(tacheDTO.getName());
        tacheDAO.setDescription(tacheDTO.getDescription());
        TacheDAO updatedTask = tacheServiceDAO.updateTask(tacheDAO);
        return new TacheDTO(
                updatedTask.getName(),
                updatedTask.getDescription(),
                updatedTask.getProject().getId(),
                updatedTask.getAssignedUser() != null ? updatedTask.getAssignedUser().getEmail() : null
        );
    }

    @Transactional
    public void deleteTask(String title, String username) {
        log.debug(ApiStrings.DELETING_TASK, title);
        var task = new TacheDAO();
        tacheServiceDAO.deleteTask(task);
    }

    @Transactional(readOnly = true)
    public List<TacheDTO> getTasksByProject(String title) {
        log.debug(ApiStrings.GETTING_TASKS, title);
        List<TacheDAO> tasksDAO = tacheServiceDAO.getTasksByProject(title);
        List<TacheDTO> tasksDTO = new ArrayList<>();
        tasksDAO.forEach(task -> tasksDTO.add(
                new TacheDTO(
                        task.getName(),
                        task.getDescription(),
                        task.getProject().getId(),
                        task.getAssignedUser() != null ? task.getAssignedUser().getEmail() : null
                )
        ));
        return tasksDTO;
    }
}

