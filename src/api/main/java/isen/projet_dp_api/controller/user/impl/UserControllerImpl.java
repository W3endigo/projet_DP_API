package isen.projet_dp_api.controller.user.impl;

import io.swagger.v3.oas.annotations.tags.Tag;
import isen.projet_dp_api.controller.user.UserController;
import isen.projet_dp_api.model.dto.UserDTO;
import isen.projet_dp_api.service.UserService;
import isen.projet_dp_api.utils.ApiStrings;
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

    public ResponseEntity<Object> registerUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug(ApiStrings.REGISTERING_USER,
                userDTO.getEmail(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getCompany());
        var requestResponseData = userService.registerUser(userDTO);
        return new ResponseEntity<>(requestResponseData, HttpStatus.CREATED);
    }
}
