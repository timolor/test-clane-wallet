package com.clane.wallet.model.dto;

import lombok.Data;

import java.util.HashMap;

/**
 * @author timolor
 * @created 29/07/2021
 */
@Data
public class UpdateKycDto {
    private String kycLevel;
    private HashMap<String, String> data;
}
