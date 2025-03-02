package isen.projet_dp_api.dao.tache;

import isen.projet_dp_api.model.dao.TacheDAO;
import java.util.List;

public interface TacheServiceDAO {

    TacheDAO createTask(TacheDAO tacheDAO);

    TacheDAO getTaskById(Integer id);

    TacheDAO updateTask(TacheDAO tacheDAO);

    void deleteTask(TacheDAO tacheDAO);

    List<TacheDAO> getTasksByProject(String title);

    TacheDAO getTaskByNameAndAssignedUserEmail(String title, String assignedUserEmail);


}