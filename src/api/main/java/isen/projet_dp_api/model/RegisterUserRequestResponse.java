package isen.projet_dp_api.model;

import java.util.Map;


public record RegisterUserRequestResponse(String status, String message, Map<String, String> details, String token) {

}
