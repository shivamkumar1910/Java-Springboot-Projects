package com.paypalclone.transactionservice.controller;

import com.paypalclone.transactionservice.dto.SendMoneyRequest;
import com.paypalclone.transactionservice.entity.Transaction;
import com.paypalclone.transactionservice.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendMoney(@Valid @RequestBody SendMoneyRequest request) {
        Transaction transaction = transactionService.sendMoney(request);
        return ResponseEntity.ok(Map.of(
                "message", "Payment successful",
                "transactionId", transaction.getId(),
                "status", transaction.getStatus()
        ));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable Long userId) {
        return ResponseEntity.ok(transactionService.getTransactions(userId));
    }
}
