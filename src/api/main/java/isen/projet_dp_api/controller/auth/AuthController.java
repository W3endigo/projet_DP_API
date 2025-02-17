package isen.projet_dp_api.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.RegisterRequestResponse;
import isen.projet_dp_api.model.dto.LoginDTO;
import isen.projet_dp_api.model.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@Tag(name = "Auth", description = "Endpoints for managing authentication operations")

public interface AuthController {

    @PostMapping("/api/auth/register")
    @Operation(summary = "Register a new user", description = "Register a new user with the provided data",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User registered successfully. The response contains no body but includes a header with the JWT token."),
                    @ApiResponse(responseCode = "400", description = "Invalid user data", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    )),
                    @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    ))
            })
    ResponseEntity<RegisterRequestResponse> registerUser(@RequestBody @Valid UserDTO userDTO);

    @PutMapping("/api/auth/login")
    @Operation(summary = "Login a user", description = "Login a user if the provided credentials are correct",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User logged in successfully. The response contains a JWT token."),
                    @ApiResponse(responseCode = "400", description = "Invalid credentials", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    )),
                    @ApiResponse(responseCode = "401", description = "Unrecognised credentials", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    )),
                    @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiException.ErrorResponse.class)
                    ))
            })
    ResponseEntity<Map<String, String>> loginUser(@RequestBody @Valid LoginDTO loginDTO);

}
