package com.crypto.service;

import com.crypto.model.TradingPrice;
import com.crypto.repository.TradingPriceRepo;
import org.springframework.stereotype.Service;

@Service
public class PriceAggregationService {

    private TradingPriceRepo tradingPriceRepo;

    public PriceAggregationService(TradingPriceRepo tradingPriceRepo) {
        this.tradingPriceRepo = tradingPriceRepo;
    }
}
