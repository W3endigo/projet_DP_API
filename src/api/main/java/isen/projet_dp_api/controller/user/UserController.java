package isen.projet_dp_api.controller.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import isen.projet_dp_api.model.ApiException;
import isen.projet_dp_api.model.RegisterRequestResponse;
import isen.projet_dp_api.model.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "User", description = "Endpoints for managing user-related operations")
public interface UserController {

}