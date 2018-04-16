package com.prem.priceparser.exceptions;

public class NotAllowedDeletingException extends GenericBusinessException {

    public NotAllowedDeletingException(String s) {
        super(s);
    }

    public NotAllowedDeletingException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
