package isen.projet_dp_api.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.dto.EmailDTO;
import isen.projet_dp_api.model.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User", description = "Endpoints for managing user-related operations")
public interface UserController {

    @PostMapping("/user")
    @Operation(summary = "Get user by email", responses = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user data", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiException.ErrorResponse.class)
            )),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiException.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiException.ErrorResponse.class)
            ))
    })
    ResponseEntity<UserDTO> getUser(@RequestBody EmailDTO emailDTO);

    @PutMapping("/user")
    @Operation(summary = "Update user", responses = {
            @ApiResponse(responseCode = "200", description = "User updated", content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user data", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiException.ErrorResponse.class)
            )),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiException.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiException.ErrorResponse.class)
            ))
    })
    ResponseEntity<String> updateUser(@RequestBody @Valid UserDTO userDTO);

}