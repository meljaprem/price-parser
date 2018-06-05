package com.prem.priceparser.exceptions;

public class GenericBusinessException extends RuntimeException {

    public GenericBusinessException(String message) {
        super(message);
    }

    public GenericBusinessException(ExceptionErrorCode errorCode) {
        super(errorCode.getErrorMessage());
    }

    public GenericBusinessException(ExceptionErrorCode errorCode, Throwable throwable) {
        super(errorCode.getErrorMessage(), throwable);
    }

    public GenericBusinessException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
