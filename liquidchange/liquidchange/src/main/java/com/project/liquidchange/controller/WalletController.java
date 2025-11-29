package com.project.liquidchange.controller;

import com.project.liquidchange.service.WalletService;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    // Feature 1: Check Balance
    // URL: http://localhost:8080/api/wallet/balance?phone=9999999999
    @GetMapping("/balance")
    public String checkBalance(@RequestParam String phone) {
        BigDecimal balance = walletService.getBalance(phone);
        return "Your Balance is: ₹" + balance;
    }

    // Feature 2: Pay Cash (The New Feature!)
    // URL: http://localhost:8080/api/wallet/pay-cash?driverPhone=...&riderPhone=...&amount=500&fare=220
    // NOTE: We are using @GetMapping temporarily so you can test it in Chrome Browser.
    // In a real app, this should be @PostMapping.
    @GetMapping("/pay-cash")
    public String driverCollectedCash(
            @RequestParam String driverPhone,
            @RequestParam String riderPhone,
            @RequestParam BigDecimal amount, // 500
            @RequestParam BigDecimal fare    // 220
    ) {
        return walletService.processCashPayment(driverPhone, riderPhone, amount, fare);
    }
    // URL: http://localhost:8080/api/wallet/withdraw?phone=8888888888&upi=priya@okaxis&amount=280
    @GetMapping("/withdraw")
    public String withdrawMoney(
            @RequestParam String phone,
            @RequestParam String upi,
            @RequestParam BigDecimal amount
    ) {
        return walletService.withdrawToBank(phone, upi, amount);
    }
}