package com.crypto.service;

import com.crypto.model.TradingPrice;
import com.crypto.model.TransactionHistory;
import com.crypto.model.UserWalletBalance;
import com.crypto.repository.TradingPriceRepo;
import com.crypto.repository.TransactionHistoryRepo;
import com.crypto.repository.UserWalletBalanceRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TradeService {
    private final UserWalletBalanceRepo walletRepo;
    private final TradingPriceRepo priceRepo;
    private final TransactionHistoryRepo historyRepo;

    public TradeService(UserWalletBalanceRepo walletRepo,
                        TradingPriceRepo priceRepo,
                        TransactionHistoryRepo historyRepo) {
        this.walletRepo = walletRepo;
        this.priceRepo = priceRepo;
        this.historyRepo = historyRepo;
    }

    public String trade(Long userId, String symbol, String action, Double quantity) {
        TradingPrice latestPrice = priceRepo.findLatestTradingPriceByCrypto(symbol);
        if (latestPrice == null) {
            return "No prices available for trading";
        }
        // Bid price use for a SELL order, ask price use for a BUY order
        Double price = action.equalsIgnoreCase("BUY") ? latestPrice.getAskPrice() : latestPrice.getBidPrice();
        Double total = price * quantity;

        // Retrieve balance to check if balance is insufficient
        List<UserWalletBalance> balances = walletRepo.findByUserId(userId);

        // Retrieve balances for both tether and crypto account
        UserWalletBalance usdtBalance = new UserWalletBalance();
        UserWalletBalance cryptoBalance = new UserWalletBalance();

        // Fetch the corresponding crypto balance
        for (UserWalletBalance balance : balances) {
            if (balance.getCurrency().equalsIgnoreCase("USDT")) {
                usdtBalance = balance;
            } else if (balance.getCurrency().equalsIgnoreCase("symbol")) {
                cryptoBalance = balance;
            }
        }

        if (action.equalsIgnoreCase("BUY")) {
            if (usdtBalance.getBalance() < total) {
                return "Insufficient USDT balance.";
            }
            usdtBalance.setBalance(usdtBalance.getBalance() - total);
            // If user buy 1 BTC, the balance will be 1
            cryptoBalance.setBalance(quantity);
        } else if (action.equalsIgnoreCase("SELL")) {
            if (cryptoBalance.getBalance() < quantity) {
                return "Insufficient crypto balance.";
            }
            cryptoBalance.setBalance(cryptoBalance.getBalance() - quantity);
            usdtBalance.setBalance(usdtBalance.getBalance() + total);
        }

        // Save the balance into DB
        walletRepo.save(cryptoBalance);
        walletRepo.save(usdtBalance);

        // Record transaction history
        TransactionHistory transaction = new TransactionHistory();
        transaction.setUserId(userId);
        transaction.setCryptoCurr(symbol);
        transaction.setAction(action);
        transaction.setQuantity(quantity);
        transaction.setPrice(price);
        transaction.setTotal(total);
        transaction.setTimestamp(LocalDateTime.now());
        historyRepo.save(transaction);

        String tradePhrase = action.equalsIgnoreCase("BUY") ? "BOUGHT" : "SOLD";
        return "You have successfully " + tradePhrase + " " + symbol + " for a total of " + total;
    }
}
