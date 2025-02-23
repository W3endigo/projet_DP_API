package isen.projet_dp_api.controller.project.impl;


import isen.projet_dp_api.controller.project.ProjectController;
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

@Log4j2
@RestController
public class ProjectControllerImpl implements ProjectController {

    private final ProjectService projectService;

    public ProjectControllerImpl(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Override
   public ResponseEntity<ProjectCreationRequestResponse> createProject(@RequestBody @Valid ProjectDTO projectDTO, @AuthenticationPrincipal UserDetails userDetails) {
        log.debug(ApiStrings.CREATING_PROJECT, projectDTO.getTitle());
        var email = userDetails.getUsername();
        var requestResponseData = projectService.createProject(projectDTO, email);
        return new ResponseEntity<>(requestResponseData, HttpStatus.CREATED);
    }
}
