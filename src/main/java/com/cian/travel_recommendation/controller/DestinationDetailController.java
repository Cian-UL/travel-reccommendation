package com.cian.travel_recommendation.controller;

import com.cian.travel_recommendation.dto.Attraction;
import com.cian.travel_recommendation.dto.HotelPrice;
import com.cian.travel_recommendation.entity.Destination;
import com.cian.travel_recommendation.repository.DestinationRepo;
import com.cian.travel_recommendation.service.AttractionService;
import com.cian.travel_recommendation.service.HotelPriceService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.util.List;

@Controller
public class DestinationDetailController {

    private final DestinationRepo destinationRepo;
    private final AttractionService attractionService;
    private final HotelPriceService hotelPriceService;

    public DestinationDetailController(DestinationRepo destinationRepo, AttractionService attractionService, HotelPriceService hotelPriceService) {
        this.destinationRepo = destinationRepo;
        this.attractionService = attractionService;
        this.hotelPriceService = hotelPriceService;
    }

    @GetMapping("/destination/{id}")
    public String detail(@PathVariable Long id, Model model) {
        Destination destination = destinationRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Destination not found: " + id));

        List<Attraction> attractions = attractionService.getAttractions(
                destination.getLatitude(), destination.getLongitude()
        );
        LocalDate checkin = LocalDate.now().plusDays(1);
        LocalDate checkout = checkin.plusDays(1);
        List<HotelPrice> hotelPrices = hotelPriceService.getHotelPrices(
                destination.getLatitude(),
                destination.getLongitude(),
                checkin.toString(),
                checkout.toString(),
                1
        );
        model.addAttribute("hotelPrices", hotelPrices);

        if (!hotelPrices.isEmpty()) {
            model.addAttribute("liveAccommodationCost", hotelPrices.get(0).getPricePerNight());
        } else {
            model.addAttribute("liveAccommodationCost", destination.getAccommodationCostPerNight());
        }

        model.addAttribute("destination", destination);
        model.addAttribute("attractions", attractions);
        return "destination-detail";
    }
}