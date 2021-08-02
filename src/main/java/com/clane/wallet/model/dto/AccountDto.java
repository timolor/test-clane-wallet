package com.clane.wallet.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author timolor
 * @created 29/07/2021
 */
@Data
@AllArgsConstructor
public class AccountDto {
    private String accountNumber;
    private String firstName;
    private String lastName;
    private String email;
}
