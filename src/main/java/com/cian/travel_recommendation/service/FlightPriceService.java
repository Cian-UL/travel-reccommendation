package com.cian.travel_recommendation.service;

import java.math.BigDecimal;

// Interface for fetching flight prices between cities
public interface FlightPriceService {

    BigDecimal getFlightPrice(String origin, String destination);

}