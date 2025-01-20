package isen.projet_dp_api.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DetailedError {
    private String message;
    private String exceptionType;
    private String location;
    private String fishTag;
    private Throwable cause;
}