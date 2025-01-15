package isen.projet_dp_api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email must be valid")
    @Size(max = 255, message = "Email must be at most 255 characters long")
    @Schema(description = "User's email address", example = "john.doe@example.com")
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 255, message = "Password must be between 8 and 255 characters long")
    @Schema(description = "User's password", example = "password123")
    private String password;

    @NotBlank(message = "First name is mandatory")
    @Pattern(regexp = "^[a-zA-Z-]*$", message = "First name must contain only letters and hyphens")
    @Size(max = 30, message = "First name must be at most 30 characters long")
    @Schema(description = "User's first name", example = "John")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Pattern(regexp = "^[a-zA-Z-]*$", message = "Last name must contain only letters and hyphens")
    @Size(max = 30, message = "Last name must be at most 30 characters long")
    @Schema(description = "User's last name", example = "Doe")
    private String lastName;

    @Pattern(regexp = "^[a-zA-Z-]*$", message = "Company name must contain only letters and hyphens")
    @Size(max = 50, message = "Company name must be at most 50 characters long")
    @Schema(description = "User's name name", example = "ACME Corp", nullable = true)
    private String company;
}