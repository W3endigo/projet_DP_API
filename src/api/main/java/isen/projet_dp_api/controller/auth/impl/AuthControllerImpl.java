package isen.projet_dp_api.controller.auth.impl;

import isen.projet_dp_api.controller.auth.AuthController;
import isen.projet_dp_api.model.dto.LoginDTO;

import isen.projet_dp_api.model.RegisterRequestResponse;
import isen.projet_dp_api.model.dto.UserDTO;
import isen.projet_dp_api.service.TokenService;
import isen.projet_dp_api.service.AuthService;
import isen.projet_dp_api.utils.ApiStrings;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Log4j2
@RestController
public class AuthControllerImpl implements AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final TokenService tokenService;

    private final AuthService authService;


    public AuthControllerImpl(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, TokenService tokenService, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.tokenService = tokenService;
        this.authService = authService;
    }

    public ResponseEntity<Map<String, String>> loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
        );
        final var userDetails = userDetailsService.loadUserByUsername(loginDTO.getEmail());
        return new ResponseEntity<>(Map.of("token", tokenService.generateToken(userDetails)), HttpStatus.OK);
    }

    public ResponseEntity<RegisterRequestResponse> registerUser(@Valid @RequestBody UserDTO userDTO) {
        log.debug(ApiStrings.REGISTERING_USER,
                userDTO.getEmail(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getCompany());
        var requestResponseData = authService.registerUser(userDTO);
        return new ResponseEntity<>(requestResponseData, HttpStatus.CREATED);
    }
}
