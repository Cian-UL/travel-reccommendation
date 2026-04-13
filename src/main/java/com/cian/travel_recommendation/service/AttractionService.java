package com.cian.travel_recommendation.service;

import com.cian.travel_recommendation.dto.Attraction;

import java.util.List;

public interface AttractionService {

    List<Attraction> getAttractions(double latitude, double longitude);
}