package com.paypalclone.walletservice.service;

import com.paypalclone.walletservice.entity.Wallet;
import com.paypalclone.walletservice.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet getWallet(Long userId) {
        return walletRepository.findByUserId(userId)
                .orElseGet(() -> walletRepository.save(Wallet.builder().userId(userId).balance(new BigDecimal("0.00")).currency("INR").build()));
    }

    @Transactional
    public Wallet addMoney(Long userId, BigDecimal amount) {
        Wallet wallet = getWallet(userId);
        wallet.setBalance(wallet.getBalance().add(amount));
        return walletRepository.save(wallet);
    }
}
