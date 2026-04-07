package com.cian.travel_recommendation.controller;

import com.cian.travel_recommendation.dto.SearchResult;
import com.cian.travel_recommendation.service.SearchService;
import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@Controller
@Validated
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping("/")
    public String home() {
        return "destinations";
    }

    @GetMapping("/search")
    public String search(@RequestParam("budget") @Min(1) BigDecimal budget,
                         @RequestParam("originCity") String originCity,
                         @RequestParam("duration") @Min(1) int duration,
                         @RequestParam("travelers") @Min(1) int travelers,
                         Model model) {

        // The controller delegates the complex logic to the service layer
        List<SearchResult> searchResults = searchService.findDestinationsWithinBudget(
                budget, originCity, duration, travelers
        );

        model.addAttribute("searchResults", searchResults);
        return "search-results";
    }
}