package com.example.wallet.controller;

import com.example.wallet.dto.AddMoneyRequest;
import com.example.wallet.dto.WalletResponse;
import com.example.wallet.service.WalletService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping
    public ResponseEntity<WalletResponse> getWallet() {
        return ResponseEntity.ok(walletService.getWalletForCurrentUser());
    }

    @PostMapping("/add-money")
    public ResponseEntity<WalletResponse> addMoney(@Valid @RequestBody AddMoneyRequest request) {
        return ResponseEntity.ok(walletService.addMoney(request));
    }
}
