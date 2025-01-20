package isen.projet_dp_api.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import isen.projet_dp_api.utils.ApiException;

import java.util.Enumeration;

@Log4j2
@ControllerAdvice
//Gestion des erreurs pour tout les @RestController
public class ApiExceptionHandler {

    // TODO: receive ApiException error and map it to a response entity
    // TODO: receive Exception error and map it to a response entity

//    @ExceptionHandler(ApiException.class)
//    public ResponseEntity<Object> handleApiException(ApiException ex, WebRequest request) {
//        log.error("API Exception: {}", ex.getMessage(), ex);
//        ApiException response = new ApiException(ex, , getRequestData(request));
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleException(Exception ex) {
//        log.error("Unexpected Exception: {}", ex.getMessage(), ex);
//        ApiException response = new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    private String getRequestData(WebRequest request) {
//        HttpServletRequest servletRequest = (HttpServletRequest) request;
//        StringBuilder requestData = new StringBuilder();
//        requestData.append("Request URI: ").append(servletRequest.getRequestURI()).append("\n");
//        requestData.append("Request Method: ").append(servletRequest.getMethod()).append("\n");
//        requestData.append("Request Parameters: ").append(servletRequest.getParameterMap().toString()).append("\n");
//        Enumeration<String> headerNames = servletRequest.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String headerName = headerNames.nextElement();
//            requestData.append("Header: ").append(headerName).append(" = ").append(servletRequest.getHeader(headerName)).append("\n");
//        }
//        return requestData.toString();
//    }
}