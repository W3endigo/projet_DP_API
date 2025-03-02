package isen.projet_dp_api.controller.tache.impl;

import isen.projet_dp_api.controller.tache.TacheController;
import isen.projet_dp_api.model.dto.TacheDTO;
import isen.projet_dp_api.service.TacheService;
import isen.projet_dp_api.utils.ApiStrings;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@RestController
public class TacheControllerImpl implements TacheController {

    private final TacheService tacheService;

    public TacheControllerImpl(TacheService tacheService) {
        this.tacheService = tacheService;
    }

    @Override
    public ResponseEntity<Void> deleteTask(String title,
                                           UserDetails userDetails) {
        log.debug(ApiStrings.DELETING_TASK, title);
        tacheService.deleteTask(title, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<TacheDTO>> getTasksByProject(String title, UserDetails userDetails) {
        log.debug(ApiStrings.GETTING_TASKS, title);
        var tasks = tacheService.getTasksByProject(title);
        var tasksDTO = new ArrayList<TacheDTO>();
        for (var task : tasks) {
            tasksDTO.add(new TacheDTO(task.getName(), task.getDescription(), task.getProjectId(), task.getAssignedUser()));
        }
        return new ResponseEntity<>(tasksDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TacheDTO> createTask(String title,
                                               TacheDTO tacheDTO,
                                               UserDetails userDetails) {
        log.debug(ApiStrings.CREATING_TASK, tacheDTO.getName(), title);
        TacheDTO createdTask = tacheService.createTask(title, tacheDTO, userDetails.getUsername());
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<TacheDTO> updateTask(@PathVariable("title") String title,
                                               @RequestBody @Valid TacheDTO tacheDTO,
                                               @AuthenticationPrincipal UserDetails userDetails) {
        log.debug(ApiStrings.UPDATING_TASK, tacheDTO.getName(), title);
        TacheDTO updatedTask = tacheService.updateTask(title, tacheDTO, userDetails.getUsername());
        return new ResponseEntity<>(updatedTask, HttpStatus.OK);
    }
}
