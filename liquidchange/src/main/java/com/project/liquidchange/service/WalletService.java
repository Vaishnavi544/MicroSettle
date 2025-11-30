package com.project.liquidchange.service;

import com.project.liquidchange.model.Transaction;
import com.project.liquidchange.model.User;
import com.project.liquidchange.model.Wallet;
import com.project.liquidchange.repository.TransactionRepository;
import com.project.liquidchange.repository.UserRepository;
import com.project.liquidchange.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class WalletService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final BankService bankService; // <--- NEW CONNECTION

    public WalletService(UserRepository userRepository,
                         WalletRepository walletRepository,
                         TransactionRepository transactionRepository,
                         BankService bankService) { // <--- Added here
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.bankService = bankService;
    }

    public BigDecimal getBalance(String phoneNumber) {
        User user = userRepository.findByPhone(phoneNumber)
                .orElseThrow(() -> new RuntimeException("User not found: " + phoneNumber));
        Wallet wallet = walletRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
        return wallet.getBalance();
    }

    @Transactional
    public String processCashPayment(String driverPhone, String riderPhone, BigDecimal cashGiven, BigDecimal rideFare) {
        BigDecimal changeAmount = cashGiven.subtract(rideFare);
        if (changeAmount.compareTo(BigDecimal.ZERO) <= 0) return "No change needed!";

        User driver = userRepository.findByPhone(driverPhone).orElseThrow();
        User rider = userRepository.findByPhone(riderPhone).orElseThrow();
        Wallet driverWallet = walletRepository.findByUserId(driver.getId()).orElseThrow();
        Wallet riderWallet = walletRepository.findByUserId(rider.getId()).orElseThrow();

        driverWallet.setBalance(driverWallet.getBalance().subtract(changeAmount));
        riderWallet.setBalance(riderWallet.getBalance().add(changeAmount));

        walletRepository.save(driverWallet);
        walletRepository.save(riderWallet);

        Transaction txn = new Transaction();
        txn.setSenderWallet(driverWallet);
        txn.setReceiverWallet(riderWallet);
        txn.setAmount(changeAmount);
        txn.setType("CASH_CHANGE");
        txn.setStatus("SUCCESS");
        transactionRepository.save(txn);

        return "Success! Transferred ₹" + changeAmount + " from Driver to Rider.";
    }

    // --- NEW FEATURE: WITHDRAW TO BANK ---
    @Transactional
    public String withdrawToBank(String phone, String upiId, BigDecimal amount) {
        // 1. Find User & Wallet
        User user = userRepository.findByPhone(phone).orElseThrow();
        Wallet wallet = walletRepository.findByUserId(user.getId()).orElseThrow();

        // 2. Check if they have enough money
        if (wallet.getBalance().compareTo(amount) < 0) {
            return "❌ Insufficient Funds! Your Balance is only: ₹" + wallet.getBalance();
        }

        // 3. DEDUCT MONEY FIRST (Safety Rule)
        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);

        // 4. CALL BANK (Simulated)
        String bankRef = bankService.sendMoneyToUserBankAccount(upiId, amount);

        // 5. Create Receipt
        Transaction txn = new Transaction();
        txn.setSenderWallet(wallet); // User is sending money out
        txn.setReceiverWallet(null); // Goes to external bank
        txn.setAmount(amount);
        txn.setType("PAYOUT_BANK");
        txn.setStatus("SUCCESS - " + bankRef);
        transactionRepository.save(txn);

        return "✅ Withdrawal Successful! ₹" + amount + " sent to " + upiId + ". Ref: " + bankRef;
    }
}