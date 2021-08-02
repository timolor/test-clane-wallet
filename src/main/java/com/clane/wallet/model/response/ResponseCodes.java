package com.clane.wallet.model.response;

import lombok.Getter;

/**
 * @author timolor
 * @created 27/07/2021
 */
public enum ResponseCodes {

    SUCCESS("00", "Successful"),
    ENTITY_NOT_FOUND("10404", "No record found for your request."),
    SYSTEM_ERROR("10500", "Something went wrong. Please try again later"),
    BAD_REQUEST("10400", "Bad Request"),
    RESOURCE_ALREADY_EXISTS("10409", "Resource already exists"),
    INSUFFICIENT_PERMISSION("100403", "Access denied.");

    private @Getter
    String code;
    private @Getter
    String message;

    ResponseCodes(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
