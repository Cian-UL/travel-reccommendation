package com.cian.travel_recommendation.service;

import java.util.List;
import com.cian.travel_recommendation.dto.HotelPrice;

// Interface for fetching hotel prices by location and dates
public interface HotelPriceService {

    List<HotelPrice> getHotelPrices(double latitude, double longitude, String checkin, String checkout, int adults);
}