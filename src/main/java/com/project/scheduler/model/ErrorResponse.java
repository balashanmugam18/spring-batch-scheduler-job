package com.project.scheduler.model;

import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@ToString
public class ErrorResponse {
    private String instance;
    private String message;
    private String detail;
    private Timestamp timestamp;

    public ErrorResponse(String instance, String message, String detail, Timestamp timestamp) {
        this.instance = instance;
        this.message = message;
        this.detail = detail;
        this.timestamp = timestamp;
    }
}
