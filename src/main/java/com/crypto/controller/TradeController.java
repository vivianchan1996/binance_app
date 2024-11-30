package com.crypto.controller;

import com.crypto.service.TradeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trade")
public class TradeController {
    private final TradeService tradeService;

    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping
    public ResponseEntity<String> trade(Long userId, String symbol, String action, Double quantity) {
        String result = tradeService.trade(userId, symbol, action, quantity);
        return ResponseEntity.ok(result);
    }
}
