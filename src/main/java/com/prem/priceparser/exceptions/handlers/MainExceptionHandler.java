package com.prem.priceparser.exceptions.handlers;

import com.prem.priceparser.exceptions.GenericBusinessException;
import com.prem.priceparser.exceptions.NotAllowedDeletingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class MainExceptionHandler {

    @ExceptionHandler(NotAllowedDeletingException.class)
    public ResponseEntity<?> notAllowedDeleting(NotAllowedDeletingException ex) {
        log.error("Wrong deleting of the object");
        return new ResponseEntity<>("Error during deleting", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GenericBusinessException.class)
    public ResponseEntity<?> business(GenericBusinessException ex) {
        log.error("Business exception occurs, more info: \n {}", ex);
        return new ResponseEntity<>("Business exception", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> allExceptionsHandler(Exception ex) {
            log.error("Unexpected error, more info: ");
            ex.printStackTrace();
        return new ResponseEntity<>("Unexpected error. Contact administrator for resolving", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
