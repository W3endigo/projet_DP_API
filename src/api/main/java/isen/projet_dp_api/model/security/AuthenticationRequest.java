package isen.projet_dp_api.model.security;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String username;
    private String password;

}