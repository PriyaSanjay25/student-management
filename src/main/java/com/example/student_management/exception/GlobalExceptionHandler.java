package com.example.student_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice  // Handles exceptions globally for all controllers
public class GlobalExceptionHandler {

    // Handle student not found
    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorResponce> handleStudentNotFound(
            StudentNotFoundException ex) {
        ErrorResponce error = new ErrorResponce(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Handle all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponce> handleGeneralException(
            Exception ex) {
        ErrorResponce error = new ErrorResponce(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Something went wrong: " + ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
