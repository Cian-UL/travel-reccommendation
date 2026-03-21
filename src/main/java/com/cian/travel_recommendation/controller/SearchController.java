package com.cian.travel_recommendation.controller;

import com.cian.travel_recommendation.entity.Destination;
import com.cian.travel_recommendation.repository.DestinationRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class SearchController {

    private final DestinationRepo destinationRepo;


    public SearchController(DestinationRepo destinationRepo) {
        this.destinationRepo = destinationRepo;
    }
    @GetMapping("/")
    public String home() {
        return "destinations";
    }

    @GetMapping("/search")
    public String search(@RequestParam("budget") BigDecimal budget,
                         @RequestParam("originCity") String originCity,
                         @RequestParam("duration") int duration,
                         @RequestParam("travelers") int travelers,
                         Model model) {

        BigDecimal totalBudget = budget;
        List<Destination> destinations = destinationRepo.findAll();
        List<SearchResult> searchResults = new ArrayList<>();

        for (Destination destination : destinations) {
            // Calculate individual costs
            BigDecimal flightCost = destination.getFlightCostEstimate().multiply(new BigDecimal(travelers));
            BigDecimal hotelCost = destination.getAccommodationCostPerNight()
                    .multiply(new BigDecimal(travelers))
                    .multiply(new BigDecimal(duration));
            BigDecimal dailyCostTotal = destination.getDailyCost()
                    .multiply(new BigDecimal(travelers))
                    .multiply(new BigDecimal(duration));

            // Calculate total
            BigDecimal totalCost = flightCost.add(hotelCost).add(dailyCostTotal);

            // Only add if within budget
            if (totalBudget.compareTo(totalCost) >= 0) {
                searchResults.add(new SearchResult(destination, flightCost, hotelCost, dailyCostTotal, totalCost));
            }
        }

        // Sort by total cost (cheapest first)
        searchResults.sort(Comparator.comparing(SearchResult::getTotalCost));

        model.addAttribute("searchResults", searchResults);
        return "search-results";
    }



    public static class SearchResult {
        private Destination destination;
        private BigDecimal flightCost;
        private BigDecimal hotelCost;
        private BigDecimal dailyCost;
        private BigDecimal totalCost;

        public SearchResult(Destination destination,
                            BigDecimal flightCost,
                            BigDecimal hotelCost,
                            BigDecimal dailyCost,
                            BigDecimal totalCost) {
            this.destination = destination;
            this.flightCost = flightCost;
            this.hotelCost = hotelCost;
            this.dailyCost = dailyCost;
            this.totalCost = totalCost;
        }

        public Destination getDestination() { return destination; }
        public BigDecimal getFlightCost() { return flightCost; }
        public BigDecimal getHotelCost() { return hotelCost; }
        public BigDecimal getDailyCost() { return dailyCost; }
        public BigDecimal getTotalCost() { return totalCost; }
    }
}

