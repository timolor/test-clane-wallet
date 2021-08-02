package com.clane.wallet.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author timolor
 * @created 27/07/2021
 */
@Data
@Entity
@Table(name = "users")
public class User extends Audit{
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private boolean isVerified;

    @ManyToOne
    @JoinColumn(name = "kyc_level_id", referencedColumnName = "id")
    private KycLevel kycLevel;
}
