package isen.projet_dp_api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCompaniesDTO {

    @NotBlank(message = "Company name is mandatory")
    @Size(max = 50, message = "Company name must be at most 50 characters long")
    @Schema(description = "Company name", example = "Apple")
    private String name;
}
