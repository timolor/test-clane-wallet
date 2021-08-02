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
@Table(name = "transactions")
public class Transaction extends Audit{
    private BigDecimal amount;
    private String paymentReference;

    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private User receiver;
}
