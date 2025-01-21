package isen.projet_dp_api.utils.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import isen.projet_dp_api.model.ApiException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


@Log4j2
@ControllerAdvice //Gestion des erreurs pour tout les @RestController
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiException.ErrorResponse> handleApiException(ApiException ex, WebRequest request) {
        // Récupération de l'URI de la requête
        var path = request.getDescription(false).replace("uri=", "");

        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ex.toErrorResponse(path));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiException.ErrorResponse> handleException(Exception ex, WebRequest request) {
        // Log de l'exception
        log.error("Unexpected error occurred", ex);

        // Récupération de l'URI de la requête
        var path = request.getDescription(false).replace("uri=", "");

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiException(ex, ErrorMessage.ERROR_INTERNAL_SERVER, HttpStatus.INTERNAL_SERVER_ERROR).toErrorResponse(path));
    }

}