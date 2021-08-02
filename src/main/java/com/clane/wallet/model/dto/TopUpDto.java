package com.clane.wallet.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author timolor
 * @created 30/07/2021
 */
@Data
public class TopUpDto {
    @NotNull
    private Long amount;
    private String paymentRef;
    @NotNull
    private Long walletId;
}
