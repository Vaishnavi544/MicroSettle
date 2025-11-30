package com.project.liquidchange;

import com.project.liquidchange.model.User;
import com.project.liquidchange.model.Wallet;
import com.project.liquidchange.repository.UserRepository;
import com.project.liquidchange.repository.WalletRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TestRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    public TestRunner(UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("---------- TEST START ----------");

        // 1. Check if Raju exists using the Repository
        String phone = "9999999999";
        User raju = userRepository.findByPhone(phone).orElse(null);

        if (raju != null) {
            System.out.println("✅ FOUND USER: " + raju.getName());

            // 2. Check his wallet
            Wallet wallet = walletRepository.findByUserId(raju.getId()).orElse(null);
            if(wallet != null) {
                System.out.println("✅ WALLET BALANCE: " + wallet.getBalance());
            } else {
                System.out.println("⚠️ Raju has no wallet!");
            }
        } else {
            System.out.println("❌ User Raju not found in DB.");
        }

        System.out.println("---------- TEST END ----------");
    }
}