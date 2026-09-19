package com.example.wallet.service;

import com.example.wallet.dto.AddMoneyRequest;
import com.example.wallet.dto.WalletResponse;
import com.example.wallet.entity.Transaction;
import com.example.wallet.entity.TransactionStatus;
import com.example.wallet.entity.TransactionType;
import com.example.wallet.entity.User;
import com.example.wallet.entity.Wallet;
import com.example.wallet.exception.ApiException;
import com.example.wallet.repository.TransactionRepository;
import com.example.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final UserService userService;

    public WalletService(WalletRepository walletRepository,
                        TransactionRepository transactionRepository,
                        UserService userService) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }

    public WalletResponse getWalletForCurrentUser() {
        User user = userService.getCurrentUser();
        Wallet wallet = walletRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ApiException("Wallet not found"));
        return new WalletResponse(wallet.getId(), wallet.getCurrency(), wallet.getBalance());
    }

    @Transactional
    public WalletResponse addMoney(AddMoneyRequest request) {
        User user = userService.getCurrentUser();
        Wallet wallet = walletRepository.findByUserId(user.getId())
                .orElseThrow(() -> new ApiException("Wallet not found"));

        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApiException("Invalid amount");
        }

        wallet.setBalance(wallet.getBalance().add(request.getAmount()));
        Wallet savedWallet = walletRepository.save(wallet);

        Transaction transaction = Transaction.builder()
                .sender(null)
                .receiver(user)
                .amount(request.getAmount())
                .message("Added Money")
                .type(TransactionType.DEPOSIT)
                .status(TransactionStatus.COMPLETED)
                .build();
        transactionRepository.save(transaction);

        return new WalletResponse(savedWallet.getId(), savedWallet.getCurrency(), savedWallet.getBalance());
    }
}
