package com.project.liquidchange.repository;

import com.project.liquidchange.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // We don't need custom methods yet, the standard ones are enough.
}