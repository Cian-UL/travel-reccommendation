package com.cian.travel_recommendation.service;

import com.cian.travel_recommendation.dto.SearchResult;
import com.cian.travel_recommendation.entity.Destination;
import com.cian.travel_recommendation.repository.DestinationRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// Orchestrates search logic and cost calculations
@Service
public class SearchService {

    private final DestinationRepo destinationRepo;
    private final FlightPriceService flightPriceService;

    public SearchService(DestinationRepo destinationRepo, FlightPriceService flightPriceService) {
        this.destinationRepo = destinationRepo;
        this.flightPriceService = flightPriceService;
    }

    // Find all destinations within user's budget
    public List<SearchResult> findDestinationsWithinBudget(BigDecimal budget, String originCity, int duration, int travelers) {
        List<Destination> destinations = destinationRepo.findAll();
        List<SearchResult> searchResults = new ArrayList<>();

        // Calculate costs for each destination
        for (Destination destination : destinations) {
            SearchResult result = calculateTripCost(destination, originCity, duration, travelers);

            if (isWithinBudget(result.getTotalCost(), budget)) {
                searchResults.add(result);
            }
        }

        // Sort by total cost ascending
        sortResultsByTotalCostAscending(searchResults);
        return searchResults;
    }

    // Calculate all costs for a destination trip
    private SearchResult calculateTripCost(Destination destination, String originCity, int duration, int travelers) {
        BigDecimal flightCost = calculateFlightCost(originCity, destination.getCityName(), travelers);
        BigDecimal hotelCost = calculateHotelCost(destination.getAccommodationCostPerNight(), duration, travelers);
        BigDecimal dailyCostTotal = calculateDailyLivingCost(destination.getDailyCost(), duration, travelers);

        BigDecimal totalCost = flightCost.add(hotelCost).add(dailyCostTotal);

        return new SearchResult(destination, flightCost, hotelCost, dailyCostTotal, totalCost);
    }

    // Get flight cost per person, then multiply by number of travelers
    private BigDecimal calculateFlightCost(String originCity, String destinationCity, int travelers) {
        return flightPriceService.getFlightPrice(originCity, destinationCity)
                .multiply(new BigDecimal(travelers));
    }

    // Hotel cost = cost per night × duration × number of travelers
    private BigDecimal calculateHotelCost(BigDecimal costPerNight, int duration, int travelers) {
        return costPerNight.multiply(new BigDecimal(travelers)).multiply(new BigDecimal(duration));
    }

    // Daily living cost = daily cost per person × duration × number of travelers
    private BigDecimal calculateDailyLivingCost(BigDecimal dailyCost, int duration, int travelers) {
        return dailyCost.multiply(new BigDecimal(travelers)).multiply(new BigDecimal(duration));
    }

    // Check if total cost is within budget
    private boolean isWithinBudget(BigDecimal totalCost, BigDecimal budget) {
        return budget.compareTo(totalCost) >= 0;
    }

    // Sort results by total cost, cheapest first
    private void sortResultsByTotalCostAscending(List<SearchResult> results) {
        results.sort(Comparator.comparing(SearchResult::getTotalCost));
    }
}