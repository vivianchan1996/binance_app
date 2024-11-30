package com.crypto.service;

import com.crypto.model.TradingPrice;
import com.crypto.repository.TradingPriceRepo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.crypto.utils.Constants;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class PriceAggregationService {

    private TradingPriceRepo tradingPriceRepo;
    private final WebClient webClient;

    public PriceAggregationService(TradingPriceRepo tradingPriceRepo) {
        this.tradingPriceRepo = tradingPriceRepo;
        this.webClient = WebClient.builder().build();
    }

    @Scheduled(fixedRate=10000)
    public void fetchAndStoreBestPrices() {
        List<Map> binanceCryptoList = fetchBinanceCrypto();
        List<Map> houbiCryptoList = fetchHoubiCrpyto();

        List<TradingPrice> results = new ArrayList<>();

        for (String symbol : List.of("ETHUSDT", "BTCUSDT")) {
            // Fetch best prices for each symbol
            Double bestBidPrice = getBestPrice(symbol, binanceCryptoList, "bidPrice", houbiCryptoList, "bid");
            Double bestAskPrice = getBestPrice(symbol, binanceCryptoList, "askPrice", houbiCryptoList, "ask");

            // Save TradingPrice
            TradingPrice bestTradingPrice = new TradingPrice();
            bestTradingPrice.setCryptoCurr(symbol);
            bestTradingPrice.setBidPrice(bestBidPrice);
            bestTradingPrice.setAskPrice(bestAskPrice);
            bestTradingPrice.setTimestamp(LocalDateTime.now());
            results.add(bestTradingPrice);
        }

        this.tradingPriceRepo.saveAll(results);

    }

    private List<Map> fetchBinanceCrypto() {
        String binanceUrl = Constants.BINANCE_URL;
        List<Map> binancePrices = webClient.get()
                .uri(binanceUrl)
                .retrieve()
                .bodyToFlux(Map.class)
                .collectList()
                .block();
        return binancePrices;
    }

    private List<Map> fetchHoubiCrpyto() {
        String huobiUrl = Constants.BINANCE_URL;
        List<Map> houbiPrices = webClient.get()
                .uri(huobiUrl)
                .retrieve()
                .bodyToFlux(Map.class)
                .collectList()
                .block();
        return houbiPrices;
    }

    private Double getBestPrice(String symbol, List<Map> firstList, String firstParam, List<Map> secondList, String secondParam) {
        double firstBid = firstList.stream()
                .filter(map -> symbol.equals(map.get("symbol")))
                .mapToDouble(map -> Double.parseDouble((String) map.get(firstParam)))
                .max()
                .orElse(Double.NaN);

        double secondBid = secondList.stream()
                .filter(map -> symbol.equalsIgnoreCase((String) map.get(secondParam)))
                .mapToDouble(map -> (double) map.get("bid"))
                .max()
                .orElse(Double.NaN);

        return Math.max(firstBid, secondBid); // Return the highest bid
    }

}
