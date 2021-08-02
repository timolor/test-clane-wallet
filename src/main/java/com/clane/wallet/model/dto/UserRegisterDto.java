package com.clane.wallet.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author timolor
 * @created 28/07/2021
 */
@Data
public class UserRegisterDto {
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "password is required")
    private String password;
}
