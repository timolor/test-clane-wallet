package com.clane.wallet.respository;

import com.clane.wallet.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author timolor
 * @created 28/07/2021
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
