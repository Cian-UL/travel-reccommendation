package com.cian.travel_recommendation.service;

import com.cian.travel_recommendation.dto.HotelPrice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Fetches live hotel prices from LiteAPI
@Service
public class LiteApiHotelPriceService implements HotelPriceService {

    private final RestClient restClient;
    private final String apiKey;

    public LiteApiHotelPriceService(@Value("${liteapi.api.key}") String apiKey) {
        this.apiKey = apiKey;
        this.restClient = RestClient.builder()
                .baseUrl("https://api.liteapi.travel/v3.0")
                .defaultHeader("X-API-Key", apiKey)
                .build();
    }

    @Override
    public List<HotelPrice> getHotelPrices(double latitude, double longitude, String checkin, String checkout, int adults) {
        try {
            // Fetch available hotels at location
            List<HotelInfo> hotels = fetchHotels(latitude, longitude);

            if (hotels.isEmpty()) {
                return List.of();
            }

            // Get pricing for top 10 hotels
            List<String> hotelIds = hotels.stream()
                    .limit(10)
                    .map(HotelInfo::id)
                    .toList();

            Map<String, Double> rates = fetchRates(hotelIds, checkin, checkout, adults);

            // Build results with pricing data
            List<HotelPrice> allResults = new ArrayList<>();
            for (HotelInfo hotel : hotels) {
                Double price = rates.get(hotel.id());
                if (price != null) {
                    allResults.add(new HotelPrice(
                            hotel.name(),
                            price,
                            hotel.stars(),
                            hotel.rating(),
                            hotel.thumbnail(),
                            hotel.address()
                    ));
                }
            }

            if (allResults.isEmpty()) {
                return List.of();
            }

            // Sort by price ascending
            allResults.sort((a, b) -> Double.compare(a.getPricePerNight(), b.getPricePerNight()));

            // Curate a diverse selection of up to 5 hotels
            List<HotelPrice> curated = new ArrayList<>();

            // Always include cheapest
            curated.add(allResults.get(0));

            // Include median-priced option
            double median = allResults.get(allResults.size() / 2).getPricePerNight();
            for (HotelPrice hp : allResults) {
                if (!curated.contains(hp) && hp.getPricePerNight() >= median) {
                    curated.add(hp);
                    break;
                }
            }

            // Include top-rated if available
            allResults.stream()
                    .filter(hp -> !curated.contains(hp) && hp.getRating() >= 9.0)
                    .findFirst()
                    .ifPresent(curated::add);

            // Include luxury option if available
            allResults.stream()
                    .filter(hp -> !curated.contains(hp) && hp.getStars() >= 5)
                    .findFirst()
                    .ifPresent(curated::add);

            // Fill remaining slots with any available hotel
            allResults.stream()
                    .filter(hp -> !curated.contains(hp))
                    .findFirst()
                    .ifPresent(curated::add);

            // Sort curated results by price
            curated.sort((a, b) -> Double.compare(a.getPricePerNight(), b.getPricePerNight()));

            List<HotelPrice> finalList = curated.stream().limit(5).toList();

            // Tag each hotel for UI display
            if (!finalList.isEmpty()) {
                finalList.get(0).setTag("Best Value");
            }
            for (HotelPrice hp : finalList) {
                if (hp.getTag() != null) continue;
                if (hp.getStars() >= 5) { hp.setTag("Luxury"); continue; }
                if (hp.getRating() >= 9.0) { hp.setTag("Top Rated"); continue; }
                hp.setTag("Mid-Range");
            }

            return finalList;

        } catch (Exception e) {
            return List.of();
        }
    }

    // Fetch list of available hotels at coordinates
    private List<HotelInfo> fetchHotels(double latitude, double longitude) {
        try {
            JsonNode response = restClient.get()
                    .uri("/data/hotels?latitude={lat}&longitude={lon}&limit=30", latitude, longitude)
                    .retrieve()
                    .body(JsonNode.class);

            List<HotelInfo> hotels = new ArrayList<>();
            if (response != null && response.has("data")) {
                for (JsonNode hotel : response.get("data")) {
                    String id = hotel.has("id") ? hotel.get("id").asText() : null;
                    String name = hotel.has("name") ? hotel.get("name").asText() : "";
                    int stars = hotel.has("stars") ? hotel.get("stars").asInt() : 0;
                    double rating = hotel.has("rating") ? hotel.get("rating").asDouble() : 0;
                    String thumbnail = hotel.has("thumbnail") ? hotel.get("thumbnail").asText() : null;
                    String address = hotel.has("address") ? hotel.get("address").asText() : "";

                    if (id != null && !name.isBlank()) {
                        hotels.add(new HotelInfo(id, name, stars, rating, thumbnail, address));
                    }
                }
            }
            return hotels;
        } catch (Exception e) {
            return List.of();
        }
    }

    // Fetch pricing for given hotels and dates
    private Map<String, Double> fetchRates(List<String> hotelIds, String checkin, String checkout, int adults) {
        try {
            Map<String, Object> body = Map.of(
                    "hotelIds", hotelIds,
                    "checkin", checkin,
                    "checkout", checkout,
                    "currency", "EUR",
                    "guestNationality", "IE",
                    "occupancies", List.of(Map.of("adults", adults, "children", List.of())),
                    "timeout", 10
            );

            JsonNode response = restClient.post()
                    .uri("/hotels/rates")
                    .header("Content-Type", "application/json")
                    .body(body)
                    .retrieve()
                    .body(JsonNode.class);

            HashMap<String, Double> rates = new HashMap<>();
            if (response != null && response.has("data")) {
                for (JsonNode hotel : response.get("data")) {
                    String hotelId = hotel.has("hotelId") ? hotel.get("hotelId").asText() : null;
                    double price = 0;

                    // Extract price from first available room type
                    if (hotel.has("roomTypes")) {
                        for (JsonNode roomType : hotel.get("roomTypes")) {
                            if (roomType.has("suggestedSellingPrice")) {
                                JsonNode sellingPrice = roomType.get("suggestedSellingPrice");
                                if (sellingPrice.has("amount")) {
                                    price = sellingPrice.get("amount").asDouble();
                                }
                            }
                            if (price > 0) break;
                        }
                    }

                    if (hotelId != null && price > 0) {
                        rates.put(hotelId, price);
                    }
                }
            }
            return rates;
        } catch (Exception e) {
            return Map.of();
        }
    }

    private record HotelInfo(String id, String name, int stars, double rating, String thumbnail, String address) {}
}