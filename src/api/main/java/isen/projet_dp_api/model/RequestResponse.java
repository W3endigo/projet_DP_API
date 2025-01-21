package isen.projet_dp_api.model;

import java.util.Map;

public record RequestResponse(String status, String message, Map<String, String> details) {

}
