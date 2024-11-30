package com.crypto.repository;

import com.crypto.model.TradingPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradingPriceRepo extends JpaRepository<TradingPrice, Long> {
    TradingPrice findLatestTradingPriceByCrypto(String symbol);
}

