package isen.projet_dp_api.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.UpdateUserRequestResponse;
import isen.projet_dp_api.model.dto.ProjectDTO;
import isen.projet_dp_api.model.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;

@Tag(name = "User", description = "Endpoints for managing user-related operations")
public interface UserController {

    @GetMapping("/api/user")
    @Operation(summary = "Get user by email", responses = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user data", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiException.ErrorResponse.class)
            )),
            @ApiResponse(responseCode = "403", description = "User not authorized", content = @Content()),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiException.ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiException.ErrorResponse.class)
            ))
    }, security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<UserDTO> getUser(@AuthenticationPrincipal UserDetails userDetails);

    @PutMapping("/api/user")
    @Operation(summary = "Update user", responses = {
            @ApiResponse(responseCode = "200", description = "User updated", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user data", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiException.ErrorResponse.class)
            )),
            @ApiResponse(responseCode = "403", description = "User not authorized", content = @Content()),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiException.ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiException.ErrorResponse.class)
            ))
    }, security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<UpdateUserRequestResponse> updateUser(@RequestBody @Valid UserDTO userDTO, @AuthenticationPrincipal UserDetails userDetails);


    @DeleteMapping("/api/user")
    @Operation(summary = "Delete user", responses = {
            @ApiResponse(responseCode = "200", description = "User deleted", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user data", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiException.ErrorResponse.class)
            )),
            @ApiResponse(responseCode = "403", description = "User not authorized", content = @Content()),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiException.ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiException.ErrorResponse.class)
            ))
    }, security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<Void> deleteUser(@AuthenticationPrincipal UserDetails userDetails);


    @GetMapping("/api/user/participations")
    @Operation(summary = "Get projects your related", responses = {
            @ApiResponse(responseCode = "200", description = "Projects found", content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid user data", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiException.ErrorResponse.class)
            )),
            @ApiResponse(responseCode = "403", description = "User not authorized", content = @Content()),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = ApiException.ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ApiException.ErrorResponse.class)
            ))
    }, security = @SecurityRequirement(name = "bearerAuth"))
    ResponseEntity<ArrayList<ProjectDTO>> getParticipations(@AuthenticationPrincipal UserDetails userDetails);
}