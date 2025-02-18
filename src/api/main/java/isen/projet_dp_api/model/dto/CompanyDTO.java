package isen.projet_dp_api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class CompanyDTO {

    @NotBlank(message = "Name is mandatory")
    @Pattern(regexp = "^[a-zA-Z0-9\\-&/ ]+$", message = "Name must contain only letters, numbers and hyphens")
    @Size(max = 30, message = "Name must be at most 30 characters long")
    @Schema(description = "Company name", example = "Apple")
    private String name;

}