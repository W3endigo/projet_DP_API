package isen.projet_dp_api.model;

import isen.projet_dp_api.model.dto.UserDTO;

import java.util.Map;

public record UpdateUserRequestResponse(String status, String message, Map<String, String> details, UserDTO dataUser) {



}
