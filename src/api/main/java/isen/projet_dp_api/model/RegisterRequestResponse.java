package isen.projet_dp_api.model;

import java.util.Map;


public record RegisterRequestResponse(String status, String message, Map<String, String> details, String token) {

    public RegisterRequestResponse(String status, String message, Map<String, String> details) {
        this(status, message, details, null);
    }


}
