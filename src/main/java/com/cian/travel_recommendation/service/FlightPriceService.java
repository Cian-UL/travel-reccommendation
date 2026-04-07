package com.cian.travel_recommendation.service;

import java.math.BigDecimal;

public interface FlightPriceService {

    BigDecimal getFlightPrice(String origin, String destination);

}