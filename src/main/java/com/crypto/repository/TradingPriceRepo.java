package com.crypto.repository;

import com.crypto.model.TradingPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradingPriceRepo extends JpaRepository<TradingPrice, Long> {
    TradingPrice findLatestTradingPriceByCryptoCurr(String symbol);
}

