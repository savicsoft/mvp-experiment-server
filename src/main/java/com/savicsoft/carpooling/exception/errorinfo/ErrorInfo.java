package com.savicsoft.carpooling.exception.errorinfo;

import lombok.Data;

@Data
public class ErrorInfo {
    private String message;
    private int statusCode;
    private long timestamp;
}
