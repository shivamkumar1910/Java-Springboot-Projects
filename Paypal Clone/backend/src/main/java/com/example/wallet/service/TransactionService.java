package com.example.wallet.service;

import com.example.wallet.entity.Transaction;
import com.example.wallet.entity.User;
import com.example.wallet.exception.ApiException;
import com.example.wallet.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserService userService;

    public TransactionService(TransactionRepository transactionRepository, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
    }

    public List<Transaction> getTransactionsForCurrentUser() {
        User currentUser = userService.getCurrentUser();
        return transactionRepository.findByUserOrderByCreatedAtDesc(currentUser);
    }

    public Transaction getTransactionById(Long id) {
        User currentUser = userService.getCurrentUser();
        return transactionRepository.findByIdAndUserId(id, currentUser.getId())
                .orElseThrow(() -> new ApiException("Transaction not found"));
    }
}
