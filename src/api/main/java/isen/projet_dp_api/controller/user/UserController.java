package isen.projet_dp_api.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User Controller", description = "Controller for managing user-related operations")
public interface UserController {

@PutMapping("/api/user")
@Operation(summary = "Register a new user", description = "Register a new user with the provided data",
        responses = {
        @ApiResponse(responseCode = "201", description = "User registered successfully. The response contains no body but includes a header with the JWT token.", content = @Content),
        @ApiResponse(responseCode = "400", description = "Invalid user data", content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiException.ErrorResponse.class)
        )),
        @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiException.ErrorResponse.class)
        ))
})
ResponseEntity<Void> registerUser(@RequestBody @Valid UserDTO userDTO);
}