package com.savicsoft.carpooling.user.exception.usererrorinfo;

import lombok.Data;

@Data
public class UserErrorInfo {
    private String message;
    private int statusCode;
    private String uriRequested;
    private long timestamp;

}
