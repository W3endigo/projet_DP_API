package isen.projet_dp_api.controller.user.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import isen.projet_dp_api.controller.user.UserController;
import isen.projet_dp_api.model.dto.UserDTO;
import isen.projet_dp_api.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@Tag(name = "User Controller", description = "Controller for managing user-related operations")
public class UserControllerImpl implements UserController {

    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<Void> registerUser(@RequestBody @Valid UserDTO userDTO) {
        try {
            log.debug("Registering user: email={}, firstName={}, lastName={}, name={}",
                    userDTO.getEmail(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getCompany());
            var jwtToken = userService.registerUser(userDTO);
            var headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwtToken);
            return new ResponseEntity<>(headers, HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error registering user", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
