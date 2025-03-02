package isen.projet_dp_api.controller.project.impl;


import isen.projet_dp_api.controller.project.ProjectController;
import isen.projet_dp_api.enums.Status;
import isen.projet_dp_api.model.ProjectCreationRequestResponse;
import isen.projet_dp_api.model.dto.ProjectDTO;
import isen.projet_dp_api.service.ProjectService;
import isen.projet_dp_api.utils.ApiStrings;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
public class ProjectControllerImpl implements ProjectController {

    private final ProjectService projectService;

    public ProjectControllerImpl(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
   public ResponseEntity<ProjectCreationRequestResponse> createProject(ProjectDTO projectDTO, UserDetails userDetails) {
        log.debug(ApiStrings.CREATING_PROJECT, projectDTO.getTitle());
        return new ResponseEntity<>(projectService.createProject(projectDTO, userDetails.getUsername()), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ProjectDTO> updateProject(ProjectDTO projectDTO, UserDetails userDetails, String title) {
        log.debug(ApiStrings.UPDATING_PROJECT, projectDTO.getTitle());
        return new ResponseEntity<>(projectService.updateProject(projectDTO, userDetails.getUsername(), title), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteProject(UserDetails userDetails, String title) {
        log.debug(ApiStrings.UPDATING_PROJECT, title);
        projectService.deleteProject(userDetails.getUsername(), title);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<ProjectDTO>> getProjectByStatus(Status status) {
        log.debug(ApiStrings.GETTING_PROJECTS, status);
        return new ResponseEntity<>(projectService.getProjectsByStatus(status), HttpStatus.OK);
    }
}
