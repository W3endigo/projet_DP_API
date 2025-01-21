package isen.projet_dp_api.model;

import lombok.Data;

import java.util.Map;

@Data
public class RequestResponse {

    private final String status;
    private final String message;
    private final Map<String, String> details;

}
