package com.crypto.service;

import com.crypto.model.UserWalletBalance;
import com.crypto.repository.UserWalletBalanceRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WalletService {
    private final UserWalletBalanceRepo walletRepo;

    public WalletService(UserWalletBalanceRepo walletRepo) {
        this.walletRepo = walletRepo;
    }

    /**
     * Retrieves wallet balances for a specific user.
     *
     * @param userId The ID of the user.
     * @return List of wallet balances.
     */
    public List<UserWalletBalance> getUserWalletBalances(Long userId) {
        return walletRepo.findAll().stream().filter(wallet -> wallet.getUserId().equals(userId)).collect(Collectors.toList());
    }

    /**
     * Updates a user's wallet balance for a specific currency.
     *
     * @param userId   The ID of the user.
     * @param currency The currency to update (e.g., USDT, BTC, ETH).
     * @param amount   The amount to add/subtract (negative values for deductions).
     * @return Updated wallet balance.
     */
    public UserWalletBalance updateWalletBalance(Long userId, String currency, double amount) {
        // Fetch the wallet balance for the user and currency
        UserWalletBalance walletBalance = walletRepo.findByUserId(userId).stream()
                .filter(wallet -> wallet.getCurrency().equalsIgnoreCase(currency))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Wallet balance for " + currency + " not found."));

        // Update the balance
        double newBalance = walletBalance.getBalance() + amount;

        if (newBalance < 0) {
            throw new RuntimeException("Insufficient balance for " + currency);
        }

        walletBalance.setBalance(newBalance);

        // Save and return the updated wallet balance
        return walletRepo.save(walletBalance);
    }
}
