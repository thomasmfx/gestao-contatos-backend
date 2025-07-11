package com.comerciosa.gestao_contatos.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.comerciosa.gestao_contatos.dto.error.ErrorResponseDTO;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerAdvisor {
    
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleResorceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            LocalDateTime.now(), 
            HttpStatus.NOT_FOUND.value(), 
            "Not found", 
            ex.getMessage(), 
            request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDTO> handleBadRequestException(BadRequestException ex, HttpServletRequest request) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            LocalDateTime.now(), 
            HttpStatus.BAD_REQUEST.value(), 
            "Bad request", 
            ex.getMessage(), 
            request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGlobalException(Exception ex, HttpServletRequest request) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
            LocalDateTime.now(), 
            HttpStatus.INTERNAL_SERVER_ERROR.value(), 
            "Internal server error", 
            "An unexpected error occurred. Please try again later.", 
            request.getRequestURI()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
