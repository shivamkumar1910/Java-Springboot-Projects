package com.example.wallet.service;

import com.example.wallet.dto.SendMoneyRequest;
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
public class PaymentService {

    private final UserService userService;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public PaymentService(UserService userService,
                         WalletRepository walletRepository,
                         TransactionRepository transactionRepository) {
        this.userService = userService;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Transaction sendMoney(SendMoneyRequest request) {
        User sender = userService.getCurrentUser();
        User receiver = userService.getUserByUsername(request.getReceiverUsername());

        if (sender.getId().equals(receiver.getId())) {
            throw new ApiException("You cannot send money to yourself");
        }

        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApiException("Invalid amount");
        }

        Wallet senderWallet = walletRepository.findByUserId(sender.getId())
                .orElseThrow(() -> new ApiException("Wallet not found"));
        Wallet receiverWallet = walletRepository.findByUserId(receiver.getId())
                .orElseThrow(() -> new ApiException("Wallet not found"));

        if (senderWallet.getBalance().compareTo(request.getAmount()) < 0) {
            throw new ApiException("Insufficient wallet balance");
        }

        senderWallet.setBalance(senderWallet.getBalance().subtract(request.getAmount()));
        receiverWallet.setBalance(receiverWallet.getBalance().add(request.getAmount()));

        walletRepository.save(senderWallet);
        walletRepository.save(receiverWallet);

        Transaction transaction = Transaction.builder()
                .sender(sender)
                .receiver(receiver)
                .amount(request.getAmount())
                .message(request.getMessage() == null || request.getMessage().isBlank() ? "Payment" : request.getMessage())
                .type(TransactionType.SENT)
                .status(TransactionStatus.COMPLETED)
                .build();

        Transaction sentTransaction = transactionRepository.save(transaction);

        Transaction receiverTransaction = Transaction.builder()
                .sender(sender)
                .receiver(receiver)
                .amount(request.getAmount())
                .message(request.getMessage() == null || request.getMessage().isBlank() ? "Payment" : request.getMessage())
                .type(TransactionType.RECEIVED)
                .status(TransactionStatus.COMPLETED)
                .build();

        transactionRepository.save(receiverTransaction);
        return sentTransaction;
    }
}
