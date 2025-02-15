package com.cathy.shopping.exception;

import io.jsonwebtoken.JwtException;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 處理 @Valid 參數驗證失敗
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors); // 回傳 400 Bad Request
    }

    // 處理 @RequestParam / @PathVariable 參數錯誤
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<String> handleMissingParams(MissingServletRequestParameterException e) {
        return ResponseEntity.badRequest().body("{ \"error\": \"Parameter " + e.getParameterName() + " is required.\"}");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        String eMessage = e.getMessage();
        String message = eMessage.substring(eMessage.indexOf("[") + 1, eMessage.indexOf("]"));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{ \"error\": \"" + message + "\"}");
    }

//    @ExceptionHandler(ServiceException.class)
//    public ResponseEntity<String> handleValidationExceptions(ServiceException e) {
//        return ResponseEntity.badRequest().body("{ \"error\": \"" + e.getMessage() + "\"}");
//    }
}
