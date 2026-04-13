package com.cian.travel_recommendation.controller;

import com.cian.travel_recommendation.dto.Attraction;
import com.cian.travel_recommendation.entity.Destination;
import com.cian.travel_recommendation.repository.DestinationRepo;
import com.cian.travel_recommendation.service.AttractionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class DestinationDetailController {

    private final DestinationRepo destinationRepo;
    private final AttractionService attractionService;

    public DestinationDetailController(DestinationRepo destinationRepo, AttractionService attractionService) {
        this.destinationRepo = destinationRepo;
        this.attractionService = attractionService;
    }

    @GetMapping("/destination/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Destination destination = destinationRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Destination not found: " + id));

        List<Attraction> attractions = attractionService.getAttractions(
                destination.getLatitude(), destination.getLongitude()
        );

        model.addAttribute("destination", destination);
        model.addAttribute("attractions", attractions);
        return "destination-detail";
    }
}