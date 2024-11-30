package com.crypto.service;

import com.crypto.repository.UserWalletBalanceRepo;
import org.springframework.stereotype.Service;

@Service
public class WalletService {
    private final UserWalletBalanceRepo walletRepo;

    public WalletService(UserWalletBalanceRepo walletRepo) {
        this.walletRepo = walletRepo;
    }
}
