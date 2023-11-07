package com.savicsoft.carpooling.user.exception.handlers;

import com.savicsoft.carpooling.user.exception.UserCreationException;
import com.savicsoft.carpooling.user.exception.UserNotFoundException;
import com.savicsoft.carpooling.user.exception.usererrorinfo.UserErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.ResponseEntity;
import java.util.Date;


@RestControllerAdvice
public class UserGlobalExceptionHandler {

    @ExceptionHandler(UserCreationException.class)
    public ResponseEntity<UserErrorInfo> handleUserCreationException(UserCreationException e){
        UserErrorInfo errorInfo = new UserErrorInfo();
        errorInfo.setMessage(e.getMessage());
        errorInfo.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorInfo.setUriRequested("/api/v1/users");
        errorInfo.setTimestamp(new Date().getTime());

        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserErrorInfo> handleUserNotFoundException(UserCreationException e){

        UserErrorInfo errorInfo = new UserErrorInfo();
        errorInfo.setMessage(e.getMessage());
        errorInfo.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorInfo.setUriRequested("/api/v1/users");
        errorInfo.setTimestamp(new Date().getTime());

        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }
}
