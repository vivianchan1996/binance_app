package com.crypto.controller;

import com.crypto.model.TradingPrice;
import com.crypto.repository.TradingPriceRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/price")
public class PriceController {
    private final TradingPriceRepo tradingPriceRepo;

    public PriceController(TradingPriceRepo tradingPriceRepo) {
        this.tradingPriceRepo = tradingPriceRepo;
    }

    @GetMapping("/latest")
    public ResponseEntity<TradingPrice> getLatestPrice(String symbol) {
        TradingPrice price = tradingPriceRepo.findLatestTradingPriceByCryptoCurrNative(symbol);
        return ResponseEntity.ok(price);
    }
}
