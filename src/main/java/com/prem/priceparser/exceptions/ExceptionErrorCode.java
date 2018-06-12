package com.prem.priceparser.exceptions;

public enum ExceptionErrorCode {
    ERROR_PRODUCT_DELETING("Product deleting is not allowed!"),
    PRODUCT_NOT_FOUND("Product not found or you don't have access to do it"),
    NO_SHOPS_TO_CHECK("Product doesn't have any assigned shop. You must add a shop to start checking!");

    private String errorMessage;

    ExceptionErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
