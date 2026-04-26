package com.cian.travel_recommendation.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class MockFlightPriceService implements FlightPriceService {

    private final Map<String, BigDecimal> basePrices = new HashMap<>();
    private final Map<String, BigDecimal> originAdjustments = new HashMap<>();

    public MockFlightPriceService() {
        basePrices.put("Paris", new BigDecimal("180.00"));
        basePrices.put("Rome", new BigDecimal("150.00"));
        basePrices.put("Barcelona", new BigDecimal("140.00"));
        basePrices.put("Amsterdam", new BigDecimal("190.00"));
        basePrices.put("Prague", new BigDecimal("110.00"));
        basePrices.put("Vienna", new BigDecimal("160.00"));
        basePrices.put("Budapest", new BigDecimal("100.00"));
        basePrices.put("Lisbon", new BigDecimal("130.00"));
        basePrices.put("Berlin", new BigDecimal("140.00"));
        basePrices.put("Athens", new BigDecimal("120.00"));
        basePrices.put("Copenhagen", new BigDecimal("200.00"));
        basePrices.put("Venice", new BigDecimal("170.00"));
        basePrices.put("Dubrovnik", new BigDecimal("160.00"));
        basePrices.put("Reykjavik", new BigDecimal("220.00"));
        basePrices.put("Zurich", new BigDecimal("240.00"));

        originAdjustments.put("Dublin", new BigDecimal("-30.00"));
        originAdjustments.put("Cork", new BigDecimal("-10.00"));
        originAdjustments.put("Shannon", new BigDecimal("-15.00"));
    }

    @Override
    public BigDecimal getFlightPrice(String origin, String destination) {
        String normalisedOrigin = origin.substring(0, 1).toUpperCase() + origin.substring(1).toLowerCase();
        BigDecimal flightPrice = basePrices.get(destination);
        BigDecimal adjustment = originAdjustments.get(normalisedOrigin);
        if (flightPrice == null) {
            flightPrice = new BigDecimal("150.00");
        }
        if (adjustment == null) {
            adjustment = BigDecimal.ZERO;
        }

        return flightPrice.add(adjustment);
    }
}