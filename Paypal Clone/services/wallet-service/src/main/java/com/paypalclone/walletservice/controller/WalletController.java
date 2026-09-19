package com.paypalclone.walletservice.controller;

import com.paypalclone.walletservice.dto.AddMoneyRequest;
import com.paypalclone.walletservice.entity.Wallet;
import com.paypalclone.walletservice.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Wallet> getWallet(@PathVariable Long userId) {
        return ResponseEntity.ok(walletService.getWallet(userId));
    }

    @PostMapping("/{userId}/add-money")
    public ResponseEntity<Map<String, Object>> addMoney(@PathVariable Long userId, @Valid @RequestBody AddMoneyRequest request) {
        Wallet wallet = walletService.addMoney(userId, request.getAmount());
        return ResponseEntity.ok(Map.of(
                "message", "Money added successfully",
                "balance", wallet.getBalance()
        ));
    }
}
