package isen.projet_dp_api.utils.exception;

import isen.projet_dp_api.utils.ApiStrings;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;

import isen.projet_dp_api.model.ApiException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;


@Log4j2
@ControllerAdvice //Gestion des erreurs pour tous les @RestController
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiException.ErrorResponse> handleApiException(ApiException ex, WebRequest request) {
        // Récupération de l'URI de la requête
        var path = request.getDescription(false).replace("uri=", "");

        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ex.toErrorResponse(path));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiException.ErrorResponse> handleApiValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        // Récupération de l'URI de la requête
        var path = request.getDescription(false).replace("uri=", "");

        var apiException = new ApiException(ex, Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage(), HttpStatus.BAD_REQUEST);

        return ResponseEntity
                .status(apiException.getHttpStatus())
                .body(apiException.toErrorResponse(path));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiException.ErrorResponse> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        // Récupération de l'URI de la requête
        var path = request.getDescription(false).replace("uri=", "");

        var apiException = new ApiException(ex, HttpStatus.UNAUTHORIZED.getReasonPhrase(), HttpStatus.UNAUTHORIZED);

        return ResponseEntity
                .status(apiException.getHttpStatus())
                .body(apiException.toErrorResponse(path));
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ApiException.ErrorResponse> handleNullPointerException(Exception ex, WebRequest request) {
        // Récupération de l'URI de la requête
        var path = request.getDescription(false).replace("uri=", "");

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiException(ex, HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND).toErrorResponse(path));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiException.ErrorResponse> handleException(Exception ex, WebRequest request) {
        // Log de l'exception
        log.error(ApiStrings.UNEXPECTED_ERROR, ex);

        // Récupération de l'URI de la requête
        var path = request.getDescription(false).replace("uri=", "");

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiException(ex, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR).toErrorResponse(path));
    }



}