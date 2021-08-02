package com.clane.wallet.respository;

import com.clane.wallet.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author timolor
 * @created 28/07/2021
 */
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUserId(long userId);
    Optional<Wallet> findByAccountNumber(String accountNumber);
}
