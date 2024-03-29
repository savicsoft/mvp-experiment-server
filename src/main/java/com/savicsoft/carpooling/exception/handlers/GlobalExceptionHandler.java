package com.savicsoft.carpooling.exception.handlers;

import com.google.cloud.storage.StorageException;
import com.savicsoft.carpooling.exception.*;
import com.savicsoft.carpooling.exception.errorinfo.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CouldNotCreateException.class,
            CouldNotUpdateException.class,
            CouldNotDeleteException.class,
            CustomDataAccessException.class,
            StorageException.class,
            IOException.class})
    public ResponseEntity<ErrorInfo> handleCarDataPersistenceException(DataPersistenceException e){

        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setMessage(e.getMessage());
        errorInfo.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorInfo.setTimestamp(new Date());

        return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorInfo> handleCarNotFoundException(NotFoundException e){

        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setMessage(e.getMessage());
        errorInfo.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorInfo.setTimestamp(new Date());

        return new ResponseEntity<>(errorInfo, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorInfo> handleIllegalArgumentException(Exception e) {

            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setMessage(e.getMessage());
            errorInfo.setStatusCode(HttpStatus.BAD_REQUEST.value());
            errorInfo.setTimestamp(new Date());

            return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MaxReachedException.class)
    public ResponseEntity<ErrorInfo> handleMaxReachedException(Exception e) {

        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setMessage(e.getMessage());
        errorInfo.setStatusCode(HttpStatus.FORBIDDEN.value());
        errorInfo.setTimestamp(new Date());

        return new ResponseEntity<>(errorInfo, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorInfo> handleUnauthorizedException(Exception e) {

        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setMessage(e.getMessage());
        errorInfo.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        errorInfo.setTimestamp(new Date());

        return new ResponseEntity<>(errorInfo, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorInfo> handleDuplicateUserException(DuplicateResourceException e) {

        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setMessage(e.getMessage());
        errorInfo.setStatusCode(HttpStatus.CONFLICT.value());
        errorInfo.setTimestamp(new Date());

        return new ResponseEntity<>(errorInfo, HttpStatus.CONFLICT);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorInfo> handleBadCredentialsException(BadCredentialsException e){
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setMessage(e.getMessage());
        errorInfo.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        errorInfo.setTimestamp(new Date());
        return new ResponseEntity<>(errorInfo, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorInfo> handleForbiddenException(ForbiddenException e){
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setMessage(e.getMessage());
        errorInfo.setStatusCode(HttpStatus.FORBIDDEN.value());
        errorInfo.setTimestamp(new Date());
        return new ResponseEntity<>(errorInfo, HttpStatus.FORBIDDEN);
    }

}
