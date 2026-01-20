package com.ecommerce.salebazar.exception;

import com.ecommerce.salebazar.common.dto.AuthResponseDTO;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            UnauthorizedException.class,
            BadCredentialsException.class
    })
    public ResponseEntity<AuthResponseDTO<Void>> handleUnauthorized(Exception ex) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(AuthResponseDTO.<Void>builder()
                        .status(false)
                        .responseCode(401)
                        .message(ex.getMessage())
                        .build());
    }


    @ExceptionHandler({
            ForbiddenException.class,
            AccessDeniedException.class
    })
    public ResponseEntity<AuthResponseDTO<Void>> handleForbidden(Exception ex) {

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(com.ecommerce.salebazar.common.dto.AuthResponseDTO.<Void>builder()
                        .status(false)
                        .responseCode(403)
                        .message("Access denied")
                        .build());
    }


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<AuthResponseDTO<Void>> handleNotFound(NotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(AuthResponseDTO.<Void>builder()
                        .status(false)
                        .responseCode(404)
                        .message(ex.getMessage())
                        .build());
    }


    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<AuthResponseDTO<Void>> handleConflict(ConflictException ex) {

        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(AuthResponseDTO.<Void>builder()
                        .status(false)
                        .responseCode(409)
                        .message(ex.getMessage())
                        .build());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<AuthResponseDTO<Void>> handleValidation(
            MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .orElse("Validation error");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(AuthResponseDTO.<Void>builder()
                        .status(false)
                        .responseCode(400)
                        .message(errorMessage)
                        .build());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<AuthResponseDTO<Void>> handleAll(Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(AuthResponseDTO.<Void>builder()
                        .status(false)
                        .responseCode(500)
                        .message("Internal server error")
                        .build());
    }
}

