package com.project.liquidchange.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Who is paying? (The Driver, technically, is giving up value)
    @ManyToOne
    @JoinColumn(name = "sender_wallet_id")
    private Wallet senderWallet;

    // Who is receiving? (The Rider)
    @ManyToOne
    @JoinColumn(name = "receiver_wallet_id")
    private Wallet receiverWallet;

    private BigDecimal amount; // The Change Amount (e.g., 280.00)

    private String type; // 'CASH_CHANGE'
    private String status; // 'SUCCESS'

    // This generates a unique ID for every receipt (Idempotency)
    private UUID transactionRefId = UUID.randomUUID();

    private LocalDateTime createdAt = LocalDateTime.now();
}