package com.project.liquidchange.service;

import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class BankService {

    // Simulates sending money via IMPS/UPI
    public String sendMoneyToUserBankAccount(String upiId, java.math.BigDecimal amount) {
        System.out.println("Connecting to Banking Network...");

        try {
            // Simulate network delay (Banks are slow!)
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Simulate a success response from the Bank
        String bankReferenceId = "BANK-" + UUID.randomUUID().toString().substring(0, 8);
        System.out.println("✅ Payment Successful to " + upiId + " | Ref: " + bankReferenceId);

        return bankReferenceId;
    }
}