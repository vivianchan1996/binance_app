package com.crypto.repository;

import com.crypto.model.TradingPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TradingPriceRepo extends JpaRepository<TradingPrice, Long> {

    @Query(value = "SELECT * FROM trading_price WHERE crypto_curr = :symbol ORDER BY timestamp DESC LIMIT 1", nativeQuery = true)
    TradingPrice findLatestTradingPriceByCryptoCurrNative(@Param("symbol") String symbol);

}

