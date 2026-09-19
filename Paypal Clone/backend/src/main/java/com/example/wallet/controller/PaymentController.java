package com.example.wallet.controller;

import com.example.wallet.dto.SendMoneyRequest;
import com.example.wallet.entity.Transaction;
import com.example.wallet.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/send")
    public ResponseEntity<Map<String, Object>> sendMoney(@Valid @RequestBody SendMoneyRequest request) {
        Transaction transaction = paymentService.sendMoney(request);
        return ResponseEntity.ok(Map.of(
                "message", "Payment successful",
                "transactionId", transaction.getId()
        ));
    }
}
