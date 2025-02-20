package isen.projet_dp_api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
public class UserDTO {

    @ToString.Exclude
    @Pattern(regexp = "^$|.{8,255}", message = "password must be between 8 and 255 characters long or empty")
    @Schema(description = "User's password", example = "password123", nullable = true)
    private String password;

    @Pattern(regexp = "^[a-zA-Z0-9\\-&/ ]*$", message = "First name must contain only letters and hyphens")
    @Size(max = 30, message = "first name must be at most 30 characters long")
    @Schema(description = "User's first name", example = "John", nullable = true)
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z0-9\\-&/ ]*$", message = "Last name must contain only letters and hyphens")
    @Size(max = 30, message = "last name must be at most 30 characters long")
    @Schema(description = "User's last name", example = "Doe", nullable = true)
    private String lastName;

    @Pattern(regexp = "^[a-zA-Z0-9\\-&/ ]*$", message = "Company name must contain only letters, numbers, and hyphens")
    @Size(max = 50, message = "company name must be at most 50 characters long")
    @Schema(description = "User's name name", example = "ACME Corp", nullable = true)
    private String company;
}
