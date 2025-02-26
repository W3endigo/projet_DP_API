package isen.projet_dp_api.controller.user.impl;

import isen.projet_dp_api.controller.user.UserController;
import isen.projet_dp_api.model.UpdateUserRequestResponse;
import isen.projet_dp_api.model.dto.ProjectDTO;
import isen.projet_dp_api.model.dto.UserDTO;
import isen.projet_dp_api.service.UserService;
import isen.projet_dp_api.utils.ApiStrings;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Log4j2
@RestController
public class UserControllerImpl implements UserController {

    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<UserDTO> getUser(@AuthenticationPrincipal UserDetails userDetails) {
        log.debug(ApiStrings.GETTING_USER, userDetails.getUsername());
        return new ResponseEntity<>(userService.getUser(userDetails.getUsername()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UpdateUserRequestResponse> updateUser(@RequestBody @Valid UserDTO updateUserDTO, @AuthenticationPrincipal UserDetails userDetails) {
        log.debug(ApiStrings.UPDATING_USER,
                userDetails.getUsername(), updateUserDTO.getFirstName(), updateUserDTO.getLastName(), updateUserDTO.getCompany());
        return new ResponseEntity<>(userService.updateUser(updateUserDTO, userDetails.getUsername()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ArrayList<ProjectDTO>> getParticipations(@AuthenticationPrincipal UserDetails userDetails) {
        log.debug(ApiStrings.GETTING_PARTICIPATIONS, userDetails.getUsername());
        return new ResponseEntity<>(userService.getProjectParticipation(userDetails), HttpStatus.OK);
    }
}