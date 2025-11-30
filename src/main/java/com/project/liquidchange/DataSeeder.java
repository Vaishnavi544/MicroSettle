package com.project.liquidchange;

import com.project.liquidchange.model.User;
import com.project.liquidchange.model.Wallet;
import com.project.liquidchange.repository.UserRepository;
import com.project.liquidchange.repository.WalletRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    public DataSeeder(UserRepository userRepository, WalletRepository walletRepository) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Create Raju (Driver) if he doesn't exist
        if (userRepository.findByPhone("9999999999").isEmpty()) {
            User raju = new User();
            raju.setName("Raju Driver");
            raju.setPhone("9999999999");
            raju.setRole("DRIVER");
            User savedRaju = userRepository.save(raju);

            Wallet rajuWallet = new Wallet();
            rajuWallet.setUser(savedRaju);
            rajuWallet.setBalance(new BigDecimal("0.00"));
            walletRepository.save(rajuWallet);

            System.out.println("✅ SEEDER: Created Raju");
        }

        // 2. Create Priya (Rider) if she doesn't exist
        if (userRepository.findByPhone("8888888888").isEmpty()) {
            User priya = new User();
            priya.setName("Priya Rider");
            priya.setPhone("8888888888");
            priya.setRole("RIDER");
            User savedPriya = userRepository.save(priya);

            Wallet priyaWallet = new Wallet();
            priyaWallet.setUser(savedPriya);
            priyaWallet.setBalance(new BigDecimal("0.00"));
            walletRepository.save(priyaWallet);

            System.out.println("✅ SEEDER: Created Priya");
        }
    }
}