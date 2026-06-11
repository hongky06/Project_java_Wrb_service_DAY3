package com.project.project_java_webservice_quanlykhoahocvachamdiem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }
        return build(HttpStatus.BAD_REQUEST, "Validation failed", fieldErrors);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegal(IllegalArgumentException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), null);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntime(RuntimeException ex) {
        String msg = ex.getMessage() == null ? "Lỗi hệ thống" : ex.getMessage();
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (msg.contains("not found") || msg.contains("không tìm thấy")) status = HttpStatus.NOT_FOUND;
        else if (msg.contains("đã tồn tại") || msg.contains("already")) status = HttpStatus.CONFLICT;
        else if (msg.contains("Invalid token") || msg.contains("Unauthorized")) status = HttpStatus.UNAUTHORIZED;
        else if (msg.contains("bị khóa")) status = HttpStatus.FORBIDDEN;
        return build(status, msg, null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error", null);
    }

    private ResponseEntity<Map<String, Object>> build(HttpStatus status, String message, Object errors) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status.value());
        body.put("message", message);
        if (errors != null) body.put("errors", errors);
        return ResponseEntity.status(status).body(body);
    }
}