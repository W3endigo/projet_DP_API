package isen.projet_dp_api.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import isen.projet_dp_api.enums.Status;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;
import java.util.List;


@Data
@ToString
@AllArgsConstructor
public class ProjectDTO {

    @NotBlank(message = "chef email is mandatory")
    @Email(message = "Email must be valid")
    @Size(max = 255, message = "email must be at most 255 characters long")
    @Schema(description = "Project chef email address", example = "john.doe@example.com")
    private String email_chef_project;

    @NotBlank(message = "Description name is mandatory")
    @Schema(description = "Project's description", example = "This is a project.")
    private String description;

    @NotBlank(message = "Title is mandatory")
    @Pattern(regexp = "^[a-zA-Z0-9- ]*$", message = "Project title must contain only letters and hyphens")
    @Size(max = 100, message = "last name must be at most 100 characters long")
    @Schema(description = "Project's name", example = "Perfect project")
    private String title;

    @NotNull(message = "Status is mandatory")
    @Schema(description = "Project's status", example = "EN_COURS")
    private Status status;

    @NotNull(message = "Project start date is mandatory")
    @Schema(description = "Project's start date", example = "10-11-2024")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date start_date;

    @NotNull(message = "Project end date is mandatory")
    @Schema(description = "Project's end date", example = "19-03-2025")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date end_date;

    @Schema(description = "List of participants")
    private List<ParticipantDTO> participants;

    @Schema(description = "List of companies")
    private List<ProjectCompaniesDTO> compagnies;

    @Schema(description = "List of tasks associated with the project")
    private List<TacheDTO> tasks;
}
