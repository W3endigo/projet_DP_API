package isen.projet_dp_api.controller.project;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.ProjectCreationRequestResponse;
import isen.projet_dp_api.model.UpdateProjectRequestResponse;
import isen.projet_dp_api.model.dto.ProjectDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Project", description = "Endpoints for managing projects-related operations")
public interface ProjectController {

    @PutMapping("/api/project")
    @Operation(summary = "Create a project", description = "Create a new project with the provided data",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Project created successfully. The response contains the projectDTO."),
                    @ApiResponse(responseCode = "400", description = "Invalid company data", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    )),
                    @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    ))
            }, security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<ProjectCreationRequestResponse> createProject(@RequestBody @Valid ProjectDTO projectDTO, @AuthenticationPrincipal UserDetails userDetails);

    @PutMapping("/api/updateProject")
    @Operation(summary = "Update project", responses = {
            @ApiResponse(responseCode = "200", description = "Project updated", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid project data", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiException.ErrorResponse.class)
            )),
            @ApiResponse(responseCode = "403", description = "User not authorized", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Project not found", content = @Content(schema = @Schema(implementation = ApiException.ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiException.ErrorResponse.class)
            ))
    }, security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<ProjectDTO> updateProject(@RequestBody @Valid ProjectDTO projectDTO, @AuthenticationPrincipal UserDetails userDetails, @RequestParam String title);
}
