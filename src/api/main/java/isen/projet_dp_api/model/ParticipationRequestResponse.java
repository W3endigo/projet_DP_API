package isen.projet_dp_api.model;

import isen.projet_dp_api.model.dto.ProjectDTO;

import java.util.List;

public record ParticipationRequestResponse(String status, String message, List<ProjectDTO> projectDTO) {
}
