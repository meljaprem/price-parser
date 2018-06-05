package com.prem.priceparser.exceptions;

public enum ExceptionErrorCode {
    ERROR_PRODUCT_DELETING("Product deleting is not allowed!"),
    PRODUCT_NOT_FOUND("Product not found or you don't have access to do it");

    private String errorMessage;

    ExceptionErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
