package com.clane.wallet.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author timolor
 * @created 28/07/2021
 */
@Data
@Entity
@Table(name = "wallets")
public class Wallet extends Audit{
    private BigDecimal balance;
    private String accountNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
