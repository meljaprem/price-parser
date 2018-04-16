package com.prem.priceparser.exceptions;

public class GenericBusinessException extends RuntimeException {

    public GenericBusinessException(String s) {
        super(s);
    }

    public GenericBusinessException(String s, Throwable throwable) {
        super(s, throwable);
    }

}
