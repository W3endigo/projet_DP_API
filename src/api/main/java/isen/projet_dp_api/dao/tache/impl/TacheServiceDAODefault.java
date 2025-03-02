package isen.projet_dp_api.dao.tache.impl;

import isen.projet_dp_api.dao.tache.TacheServiceDAO;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.dao.TacheDAO;
import isen.projet_dp_api.repository.TacheRepository;
import isen.projet_dp_api.utils.exception.ErrorMessage;
import isen.projet_dp_api.utils.exception.LogExceptionUtils;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TacheServiceDAODefault implements TacheServiceDAO {

    private final TacheRepository tacheRepository;

    public TacheServiceDAODefault(TacheRepository tacheRepository) {
        this.tacheRepository = tacheRepository;
    }

    @Override
    public TacheDAO createTask(TacheDAO tacheDAO) {
        try {
            return tacheRepository.save(tacheDAO);
        } catch (JpaObjectRetrievalFailureException e) {
            LogExceptionUtils.logException(this.getClass(),
                    ErrorMessage.ERROR_CREATING_TASK, e, tacheDAO);
            throw new ApiException(e, ErrorMessage.ERROR_CREATING_TASK, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public TacheDAO getTaskById(Integer id) {
        return tacheRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
    }

    @Override
    public void deleteTask(String title, Integer taskId, String username) {
        //TODO
    }

    @Override
    public TacheDAO updateTask(TacheDAO tacheDAO) {
        try {
            return tacheRepository.save(tacheDAO);
        } catch (JpaObjectRetrievalFailureException e) {
            LogExceptionUtils.logException(this.getClass(),
                    ErrorMessage.ERROR_UPDATING_TASK, e, tacheDAO);
            throw new ApiException(e, ErrorMessage.ERROR_UPDATING_TASK, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void deleteTask(TacheDAO tacheDAO) {
        TacheDAO tache = getTaskById(tacheDAO.getId());
        tacheRepository.delete(tache);
    }

    @Override
    public List<TacheDAO> getTasksByProject(String title) {
        return tacheRepository.findByProjectTitle(title)
                .orElseThrow(() -> new EntityNotFoundException("No tasks found for project with title: " + title));

    }
}
