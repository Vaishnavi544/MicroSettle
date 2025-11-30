package com.project.liquidchange.repository;

import com.project.liquidchange.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    // We need this to find a wallet belonging to a specific rider/driver
    Optional<Wallet> findByUserId(Long userId);
}