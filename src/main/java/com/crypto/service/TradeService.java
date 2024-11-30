package com.crypto.service;

import com.crypto.repository.TradingPriceRepo;
import com.crypto.repository.TransactionHistoryRepo;
import com.crypto.repository.UserWalletBalanceRepo;
import org.springframework.stereotype.Service;

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
}
