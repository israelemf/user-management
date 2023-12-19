package com.israelemf.userManagementSystem.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class ErrorStructure {
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime timestamp;
    private int code;
    private String status;
    private String error;

    public ErrorStructure(){

    }

    public ErrorStructure(LocalDateTime timestamp, int code, String status, String error) {
        this.timestamp = timestamp;
        this.code = code;
        this.status = status;
        this.error = error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }
}
