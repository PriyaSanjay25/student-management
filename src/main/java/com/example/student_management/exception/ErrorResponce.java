package com.example.student_management.exception;

import java.time.LocalDateTime;

public class ErrorResponce {

    private int status;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponce(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    // Getters
    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
}