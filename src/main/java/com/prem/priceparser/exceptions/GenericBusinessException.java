package com.prem.priceparser.exceptions;

public class GenericBusinessException extends RuntimeException {

    private ExceptionErrorCode errorCode;

    public GenericBusinessException(String message) {
        super(message);
    }

    public GenericBusinessException(ExceptionErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }

    public GenericBusinessException(ExceptionErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public GenericBusinessException(ExceptionErrorCode errorCode, Throwable throwable) {
        super(errorCode.getErrorMessage(), throwable);
        this.errorCode = errorCode;
    }

    public GenericBusinessException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ExceptionErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ExceptionErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
