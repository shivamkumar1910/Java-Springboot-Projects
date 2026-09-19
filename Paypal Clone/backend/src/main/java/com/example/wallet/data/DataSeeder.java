package com.example.wallet.data;

import com.example.wallet.entity.User;
import com.example.wallet.entity.Wallet;
import com.example.wallet.repository.UserRepository;
import com.example.wallet.repository.WalletRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedData(UserRepository userRepository,
                                      WalletRepository walletRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.count() == 0) {
                User alice = User.builder()
                        .name("Alice")
                        .username("alice")
                        .email("alice@example.com")
                        .password(passwordEncoder.encode("password123"))
                        .build();
                alice = userRepository.save(alice);

                User bob = User.builder()
                        .name("Bob")
                        .username("bob")
                        .email("bob@example.com")
                        .password(passwordEncoder.encode("password123"))
                        .build();
                bob = userRepository.save(bob);

                walletRepository.save(Wallet.builder().user(alice).balance(new BigDecimal("10000.00")).currency("INR").build());
                walletRepository.save(Wallet.builder().user(bob).balance(new BigDecimal("5000.00")).currency("INR").build());
            }
        };
    }
}
