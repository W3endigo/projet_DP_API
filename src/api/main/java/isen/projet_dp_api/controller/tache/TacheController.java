package isen.projet_dp_api.controller.tache;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.dto.TacheDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Task", description = "Endpoints for managing tasks related to projects")
public interface TacheController {

    @PostMapping("/api/projects/{title}/tasks")
    @Operation(
            summary = "Create a task",
            description = "Create a new task associated with a given project",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Task created successfully. The response contains the TacheDTO."),
                    @ApiResponse(responseCode = "400", description = "Invalid task data", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    )),
                    @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<TacheDTO> createTask(@PathVariable("title") String title,
                                        @RequestBody @Valid TacheDTO tacheDTO,
                                        @AuthenticationPrincipal UserDetails userDetails);

    @PutMapping("/api/projects/{title}/tasks")
    @Operation(
            summary = "Update task",
            description = "Update an existing task associated with a project",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task updated successfully", content = @Content(schema = @Schema(implementation = TacheDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid task data", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Task not found", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    )),
                    @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<TacheDTO> updateTask(@PathVariable("title") String title,
                                        @RequestParam Integer taskId,
                                        @RequestBody @Valid TacheDTO tacheDTO,
                                        @AuthenticationPrincipal UserDetails userDetails);

    @DeleteMapping("/api/projects/{title}/tasks")
    @Operation(
            summary = "Delete task",
            description = "Delete a task associated with a given project",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Task deleted successfully."),
                    @ApiResponse(responseCode = "400", description = "Invalid task data", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "Task not found", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    )),
                    @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<Void> deleteTask(@PathVariable("title") String title,
                                    @RequestParam Integer taskId,
                                    @AuthenticationPrincipal UserDetails userDetails);

    @GetMapping("/api/projects/{title}/tasks")
    @Operation(
            summary = "Get tasks by project",
            description = "Retrieve all tasks associated with a given project",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TacheDTO.class)
                    )),
                    @ApiResponse(responseCode = "404", description = "No tasks found for the project", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    )),
                    @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    ))
            }
    )
    ResponseEntity<List<TacheDTO>> getTasksByProject(@PathVariable("title") String title);
}
