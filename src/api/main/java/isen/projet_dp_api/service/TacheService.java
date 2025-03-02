package isen.projet_dp_api.service;

import isen.projet_dp_api.dao.tache.TacheServiceDAO;
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

    public TacheService(TacheServiceDAO tacheServiceDAO) {
        this.tacheServiceDAO = tacheServiceDAO;
    }

    @Transactional
    public TacheDTO createTask(String title, TacheDTO tacheDTO, String username) {
        log.debug(ApiStrings.CREATING_TASK, tacheDTO.getName(), title);
        TacheDAO tacheDAO = new TacheDAO();
        tacheDAO.setName(tacheDTO.getName());
        tacheDAO.setDescription(tacheDTO.getDescription());

        TacheDAO createdTask = tacheServiceDAO.createTask(tacheDAO);
        return new TacheDTO(
                createdTask.getName(),
                createdTask.getDescription(),
                createdTask.getProject().getId(),
                createdTask.getAssignedUser() != null ? createdTask.getAssignedUser().getEmail() : null
        );
    }

    @Transactional
    public TacheDTO updateTask(String title, Integer taskId, TacheDTO tacheDTO, String username) {
        log.debug(ApiStrings.UPDATING_TASK, tacheDTO.getName(), title, taskId);
        TacheDAO tacheDAO = new TacheDAO();
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
    public void deleteTask(String title, Integer taskId, String username) {
        log.debug(ApiStrings.DELETING_TASK, taskId, title);
        tacheServiceDAO.deleteTask(title, taskId, username);
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

