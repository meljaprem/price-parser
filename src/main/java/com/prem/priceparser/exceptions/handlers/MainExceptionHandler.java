package com.prem.priceparser.exceptions.handlers;

import com.prem.priceparser.exceptions.GenericBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class MainExceptionHandler {


    @ExceptionHandler(GenericBusinessException.class)
    public ResponseEntity<?> business(GenericBusinessException ex) {
        log.error("Business exception occurs, more info: \n");
        ex.printStackTrace();
        return new ResponseEntity<>(ex.getErrorCode() + ": " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> business(HttpRequestMethodNotSupportedException ex) {
        log.error("Someone sent request to url with unsupported http method: ", ex);
        return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        log.error("Invalid parameters in request were sent ", ex);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> allExceptionsHandler(Exception ex) {
            log.error("Unexpected error, more info: ");
            ex.printStackTrace();
        return new ResponseEntity<>("Unexpected error. Contact administrator for resolving", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
