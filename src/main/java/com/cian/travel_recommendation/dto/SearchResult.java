package com.cian.travel_recommendation.dto;

import com.cian.travel_recommendation.entity.Destination;
import java.math.BigDecimal;

public class SearchResult {
    private Destination destination;
    private BigDecimal flightCost;
    private BigDecimal accommodationCost;
    private BigDecimal dailyCost;
    private BigDecimal totalCost;

    public SearchResult(Destination destination,
                        BigDecimal flightCost,
                        BigDecimal accommodationCost,
                        BigDecimal dailyCost,
                        BigDecimal totalCost) {
        this.destination = destination;
        this.flightCost = flightCost;
        this.accommodationCost = accommodationCost;
        this.dailyCost = dailyCost;
        this.totalCost = totalCost;
    }

    public Destination getDestination() { return destination; }
    public BigDecimal getFlightCost() { return flightCost; }
    public BigDecimal getAccommodationCost() { return accommodationCost; }
    public BigDecimal getDailyCost() { return dailyCost; }
    public BigDecimal getTotalCost() { return totalCost; }
}