package com.crypto.service;

import com.crypto.model.TradingPrice;
import com.crypto.repository.TradingPriceRepo;
import jakarta.transaction.Transactional;
import org.springframework.core.ParameterizedTypeReference;
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
        this.webClient = WebClient.builder().codecs(configurer ->
                configurer.defaultCodecs().maxInMemorySize(10 * 1024 * 1024) // 10 MB
        ).build();
    }

    @Scheduled(fixedRate=10000)
    @Transactional
    public void fetchAndStoreBestPrices() {
        List<Map> binanceCryptoList = fetchBinanceCrypto();
        List<Map<String, Object>> houbiCryptoList = fetchHoubiCrpyto();

        //List<TradingPrice> results = new ArrayList<>();

        for (String symbol : List.of("ETHUSDT", "BTCUSDT")) {
            // Fetch best prices for each symbol
            Double bestBidPrice = getBestPrice("bid",symbol, binanceCryptoList, "bidPrice", houbiCryptoList, "bid");
            Double bestAskPrice = getBestPrice("ask",symbol, binanceCryptoList, "askPrice", houbiCryptoList, "ask");

            // Save TradingPrice
            TradingPrice bestTradingPrice = new TradingPrice();
            bestTradingPrice.setCryptoCurr(symbol);
            bestTradingPrice.setBidPrice(bestBidPrice);
            bestTradingPrice.setAskPrice(bestAskPrice);
            bestTradingPrice.setTimestamp(LocalDateTime.now());
            //TODO debug why it's not persisted into DB
            this.tradingPriceRepo.save(bestTradingPrice);
        }

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

    private List<Map<String, Object>> fetchHoubiCrpyto() {
        String huobiUrl = Constants.HUOBI_URL;
        List<Map<String, Object>> dataList = webClient.get()
                .uri(huobiUrl)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {}) // Parse the root JSON object
                .map(response -> (List<Map<String, Object>>) response.get("data")) // Extract the `data` field
                .block();

        if (dataList == null) {
            System.err.println("No data available in the response!");
            dataList = List.of(); // Return an empty list if `data` is null
        }

        return dataList;
    }

    private Double getBestPrice(String trade, String symbol, List<Map> firstList, String firstParam, List<Map<String, Object>> secondList, String secondParam) {
        double firstBid = firstList.stream()
                .filter(map -> symbol.equalsIgnoreCase((String) map.get("symbol"))) // Match symbol
                .mapToDouble(map -> {
                    try {
                        String value = (String) map.get(firstParam);
                        return Double.parseDouble(value);
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format for " + firstParam + ": " + map.get(firstParam));
                        return Double.NaN;
                    }
                })
                .max()
                .orElse(Double.NaN); // Default to NaN if no valid values

        double secondBid = secondList.stream()
                .filter(map -> symbol.equalsIgnoreCase((String) map.get("symbol"))) // Match symbol
                .mapToDouble(map -> {
                    try {
                        Double value = (Double) map.get(secondParam);
                        return value;
                    } catch (NumberFormatException e) {
                        System.err.println("Invalid number format for " + firstParam + ": " + map.get(firstParam));
                        return Double.NaN;
                    }
                })
                .findFirst()
                .orElse(Double.NaN); //

        Double bestPrice = 0.0;
        if (trade.equalsIgnoreCase("bid")) {
            bestPrice= Math.max(firstBid, secondBid); // Return the highest bid price
        } else if (trade.equalsIgnoreCase("ask")) {
            bestPrice= Math.min(firstBid, secondBid); // Return the lowest ask price
        }
        return bestPrice;
    }

}
