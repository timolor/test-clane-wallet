package com.clane.wallet.api;

import com.clane.wallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author timolor
 * @created 28/07/2021
 */
@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionApi {
    private final TransactionService transactionService;

    @Autowired
    public TransactionApi(TransactionService transactionService){
        this.transactionService = transactionService;
    }
}
