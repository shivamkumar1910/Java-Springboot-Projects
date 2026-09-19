package com.paypalclone.transactionservice.service;

import com.paypalclone.transactionservice.dto.SendMoneyRequest;
import com.paypalclone.transactionservice.entity.Transaction;
import com.paypalclone.transactionservice.entity.TransactionStatus;
import com.paypalclone.transactionservice.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Transaction sendMoney(SendMoneyRequest request) {
        if (request.getSenderId().equals(request.getReceiverId())) {
            throw new IllegalArgumentException("Sender and receiver cannot be the same person");
        }

        Transaction transaction = Transaction.builder()
                .senderId(request.getSenderId())
                .receiverId(request.getReceiverId())
                .amount(request.getAmount())
                .message(request.getMessage())
                .status(TransactionStatus.SUCCESS)
                .createdAt(LocalDateTime.now())
                .build();

        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactions(Long userId) {
        return transactionRepository.findBySenderIdOrReceiverId(userId, userId);
    }
}
