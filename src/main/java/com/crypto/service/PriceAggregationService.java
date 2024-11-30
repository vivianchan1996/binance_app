package com.crypto.service;

import com.crypto.model.TradingPrice;
import com.crypto.repository.TradingPriceRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class PriceAggregationService {

    private TradingPriceRepo tradingPriceRepo;
    private final RestTemplate restTemplate;

    public PriceAggregationService(TradingPriceRepo tradingPriceRepo) {
        this.tradingPriceRepo = tradingPriceRepo;
        this.restTemplate = new RestTemplate();
    }
}
