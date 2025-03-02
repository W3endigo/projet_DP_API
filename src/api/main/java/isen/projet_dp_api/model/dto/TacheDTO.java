package isen.projet_dp_api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@Schema(description = "Data Transfer Object for a Task")
public class TacheDTO {

    @NotBlank(message = "Task name is mandatory")
    @Size(max = 100, message = "Task name must be at most 100 characters long")
    @Schema(description = "Task's name", example = "Design Homepage")
    private String name;

    @NotBlank(message = "Task description is mandatory")
    @Schema(description = "Task's description", example = "Design the layout for the homepage")
    private String description;

    @Schema(description = "Identifier of the project this task is associated with", example = "1")
    private Integer projectId;

    @Schema(description = "Email of the assigned user (optional)", example = "jane.doe@example.com")
    private String assignedUser;
}