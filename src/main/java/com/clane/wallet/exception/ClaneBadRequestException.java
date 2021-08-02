package com.clane.wallet.exception;

import lombok.Getter;

/**
 * @author timolor
 * @created 27/07/2021
 */
public class ClaneBadRequestException extends RuntimeException {
    @Getter
    private String code;

    public ClaneBadRequestException(String message, String code) {
        super(message);
        this.code = code;
    }
}
