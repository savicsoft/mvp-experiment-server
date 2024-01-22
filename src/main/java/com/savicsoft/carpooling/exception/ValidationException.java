package com.savicsoft.carpooling.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidationException extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", new Date());
        responseBody.put("status", status.value());

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        List<Map<String, String>> invalidFields = fieldErrors.stream()
                .map(fieldError -> {
                    Map<String, String> fieldErrorMap = new LinkedHashMap<>();
                    fieldErrorMap.put("field", fieldError.getField());
                    if (Objects.requireNonNull(fieldError.getDefaultMessage()).contains("Failed to convert value")){
                        fieldErrorMap.put("error", "Type Mismatch Error. Make sure to use the correct data types");
                    }
                    fieldErrorMap.put("error", fieldError.getDefaultMessage());
                    return fieldErrorMap;
                })
                .collect(Collectors.toList());

        responseBody.put("invalidFields", invalidFields);

        return new ResponseEntity<>(responseBody, headers, status);
    }
}
