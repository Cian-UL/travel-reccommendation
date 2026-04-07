package com.cian.travel_recommendation.service;

import com.cian.travel_recommendation.dto.SearchResult;
import com.cian.travel_recommendation.entity.Destination;
import com.cian.travel_recommendation.repository.DestinationRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class SearchService {

    private final DestinationRepo destinationRepo;
    private final FlightPriceService flightPriceService;

    public SearchService(DestinationRepo destinationRepo, FlightPriceService flightPriceService) {
        this.destinationRepo = destinationRepo;
        this.flightPriceService = flightPriceService;
    }

    public List<SearchResult> findDestinationsWithinBudget(BigDecimal budget, String originCity, int duration, int travelers) {
        List<Destination> destinations = destinationRepo.findAll();
        List<SearchResult> searchResults = new ArrayList<>();

        for (Destination destination : destinations) {
            SearchResult result = calculateTripCost(destination, originCity, duration, travelers);

            if (isWithinBudget(result.getTotalCost(), budget)) {
                searchResults.add(result);
            }
        }

        sortResultsByTotalCostAscending(searchResults);
        return searchResults;
    }

    private SearchResult calculateTripCost(Destination destination, String originCity, int duration, int travelers) {
        BigDecimal flightCost = calculateFlightCost(originCity, destination.getCityName(), travelers);
        BigDecimal hotelCost = calculateHotelCost(destination.getAccommodationCostPerNight(), duration, travelers);
        BigDecimal dailyCostTotal = calculateDailyLivingCost(destination.getDailyCost(), duration, travelers);

        BigDecimal totalCost = flightCost.add(hotelCost).add(dailyCostTotal);

        return new SearchResult(destination, flightCost, hotelCost, dailyCostTotal, totalCost);
    }

    private BigDecimal calculateFlightCost(String originCity, String destinationCity, int travelers) {
        return flightPriceService.getFlightPrice(originCity, destinationCity)
                .multiply(new BigDecimal(travelers));
    }

    private BigDecimal calculateHotelCost(BigDecimal costPerNight, int duration, int travelers) {
        return costPerNight.multiply(new BigDecimal(travelers)).multiply(new BigDecimal(duration));
    }

    private BigDecimal calculateDailyLivingCost(BigDecimal dailyCost, int duration, int travelers) {
        return dailyCost.multiply(new BigDecimal(travelers)).multiply(new BigDecimal(duration));
    }

    private boolean isWithinBudget(BigDecimal totalCost, BigDecimal budget) {
        return budget.compareTo(totalCost) >= 0;
    }

    private void sortResultsByTotalCostAscending(List<SearchResult> results) {
        results.sort(Comparator.comparing(SearchResult::getTotalCost));
    }
}