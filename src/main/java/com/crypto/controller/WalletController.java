package com.crypto.controller;

import com.crypto.model.UserWalletBalance;
import com.crypto.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserWalletBalance>> getUserWalletBalances(@PathVariable Long userId) {
        List<UserWalletBalance> balances = walletService.getUserWalletBalances(userId);
        return ResponseEntity.ok(balances);
    }
}
