package isen.projet_dp_api.model;

import isen.projet_dp_api.model.dto.ProjectDTO;

import java.util.Map;

public record ProjectCreationRequestResponse(String status, String message, Map<String, String> details, ProjectDTO projectDTO) {

}
