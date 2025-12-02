package com.example.demo.dto.response;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class ErrorResponse {


        private String message;
        private int status;
        private LocalDateTime timestamp;
        private String path;
        private List<String> details;

        public ErrorResponse() {
            this.timestamp = LocalDateTime.now();
        }

        public ErrorResponse(String message, int status, String path) {
            this();
            this.message = message;
            this.status = status;
            this.path = path;
        }
    }