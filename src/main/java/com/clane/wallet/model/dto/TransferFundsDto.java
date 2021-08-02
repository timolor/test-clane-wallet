package com.clane.wallet.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author timolor
 * @created 30/07/2021
 */
@Data
public class TransferFundsDto {
    @NotBlank(message = "account number is required")
    private String accountNumber;
    private Long amount;
}
