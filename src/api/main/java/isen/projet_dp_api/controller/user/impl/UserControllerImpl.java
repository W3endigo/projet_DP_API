package isen.projet_dp_api.controller.user.impl;

import isen.projet_dp_api.controller.user.UserController;
import isen.projet_dp_api.model.dto.EmailDTO;
import isen.projet_dp_api.model.dto.UserDTO;
import isen.projet_dp_api.service.UserService;
import isen.projet_dp_api.utils.ApiStrings;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class UserControllerImpl implements UserController {

    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<UserDTO> getUser(@RequestBody @Valid EmailDTO emailDTO) {
        log.debug(ApiStrings.GETTING_USER, emailDTO.getEmail());
        return new ResponseEntity<>(userService.getUser(emailDTO), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateUser(@RequestBody @Valid UserDTO userDTO) {
        log.debug(ApiStrings.UPDATING_USER,
                userDTO.getEmail(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getCompany());
        userService.updateUser(userDTO);
        return new ResponseEntity<>("Updated", HttpStatus.OK);

    }
}