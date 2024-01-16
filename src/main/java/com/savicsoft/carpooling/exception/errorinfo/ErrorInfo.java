package com.savicsoft.carpooling.exception.errorinfo;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorInfo {
    private String message;
    private int statusCode;
    private Date timestamp;
}
