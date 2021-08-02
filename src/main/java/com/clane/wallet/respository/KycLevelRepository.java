package com.clane.wallet.respository;

import com.clane.wallet.model.KycLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author timolor
 * @created 28/07/2021
 */
@Repository
public interface KycLevelRepository extends JpaRepository<KycLevel, Long> {
    Optional<KycLevel> findByName(String name);
}
