package isen.projet_dp_api.model;

import isen.projet_dp_api.utils.FishTagFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;


@Data
@Log4j2
@EqualsAndHashCode(callSuper = true)
public class ApiException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String fishTag;
    private final HttpStatus httpStatus;
    private final String message; // Error message
    private final ZonedDateTime  timestamp; // Time when the error occurred


    public ApiException(Exception exception, String message, HttpStatus status) {
        super(exception);
        this.fishTag = FishTagFilter.getFishTag();
        this.httpStatus = status;
        this.message = message;
        this.timestamp = Instant.now().atZone(ZoneId.of("Europe/Paris"));
    }

    public ApiException(String message, HttpStatus status) {
        super(message);
        this.fishTag = FishTagFilter.getFishTag();
        this.httpStatus = status;
        this.message = message;
        this.timestamp = Instant.now().atZone(ZoneId.of("Europe/Paris"));
    }

    @Data
    public static class ErrorResponse {
        private final String message;
        private final ZonedDateTime timestamp;
        private final String path;
        private final String fishTag;
    }

    public ErrorResponse toErrorResponse(String path) {
        return new ErrorResponse(message, timestamp, path, fishTag);
    }

}