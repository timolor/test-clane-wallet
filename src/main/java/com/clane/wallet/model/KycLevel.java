package com.clane.wallet.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author timolor
 * @created 28/07/2021
 */
@Data
@Entity
@Table(name = "kyc_levels")
public class KycLevel extends Audit{
    private String name;
    private Double kycLimit;
}