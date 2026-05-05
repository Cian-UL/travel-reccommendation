package com.cian.travel_recommendation.service;

import com.cian.travel_recommendation.dto.Attraction;
import tools.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

// Fetches attractions from OpenTripMap API
@Service
public class OpenTripMapAttractionService implements AttractionService {

    private final RestClient restClient;
    private final String apiKey;

    public OpenTripMapAttractionService(@Value("${opentripmap.api.key}") String apiKey) {
        this.apiKey = apiKey;
        this.restClient = RestClient.builder()
                .baseUrl("https://api.opentripmap.com/0.1/en/places")
                .build();
    }

    @Override
    public List<Attraction> getAttractions(double latitude, double longitude) {
        try {
            // Get nearby attractions within 15km radius
            JsonNode response = restClient.get()
                    .uri("/radius?radius=15000&lon={lon}&lat={lat}&kinds=cultural,historic,architecture,natural,religion,monuments_and_memorials&limit=100&rate=3&apikey={key}",
                            longitude, latitude, apiKey)
                    .retrieve()
                    .body(JsonNode.class);

            List<Attraction> attractions = new ArrayList<>();

            if (response != null && response.has("features")) {
                // Extract XIDs (place identifiers) from search results
                List<String> xids = new ArrayList<>();
                for (JsonNode feature : response.get("features")) {
                    JsonNode properties = feature.get("properties");
                    String name = properties.has("name") ? properties.get("name").asText() : "";
                    int rating = properties.has("rate") ? properties.get("rate").asInt() : 0;
                    String xid = properties.has("xid") ? properties.get("xid").asText() : null;

                    if (!name.isBlank() && xid != null) {
                        xids.add(xid);
                    }
                }

                // Limit to top 10 attractions for detail fetching
                List<String> topXids = xids.size() > 10 ? xids.subList(0, 10) : xids;

                // Fetch detailed information for each attraction
                for (String xid : topXids) {
                    Attraction detail = fetchPlaceDetail(xid);
                    if (detail != null) {
                        attractions.add(detail);
                    }
                }

                // Sort by rating and cap at 10 results
                attractions.sort((a, b) -> Integer.compare(b.getRating(), a.getRating()));

                if (attractions.size() > 10) {
                    attractions = new ArrayList<>(attractions.subList(0, 10));
                }
            }

            return attractions;

        } catch (Exception e) {
            return List.of();
        }
    }

    // Fetch full details for a single attraction by XID
    private Attraction fetchPlaceDetail(String xid) {
        try {
            JsonNode detail = restClient.get()
                    .uri("/xid/{xid}?apikey={key}", xid, apiKey)
                    .retrieve()
                    .body(JsonNode.class);

            if (detail == null) {
                return null;
            }

            String name = detail.has("name") ? detail.get("name").asText() : "";
            if (name.isBlank()) {
                return null;
            }

            // Extract category from kinds field
            String category = detail.has("kinds") ? detail.get("kinds").asText().split(",")[0] : "attraction";
            int rating = detail.has("rate") ? detail.get("rate").asInt() : 0;

            // Get Wikipedia extract up to 200 chars
            String description = null;
            if (detail.has("wikipedia_extracts") && detail.get("wikipedia_extracts").has("text")) {
                description = detail.get("wikipedia_extracts").get("text").asText();
                if (description.length() > 200) {
                    description = description.substring(0, 197) + "...";
                }
            }

            // Get preview image
            String imageUrl = null;
            if (detail.has("preview") && detail.get("preview").has("source")) {
                imageUrl = detail.get("preview").get("source").asText();
            }

            // Get official website URL
            String websiteUrl = null;
            if (detail.has("url") && !detail.get("url").asText().isBlank()) {
                websiteUrl = detail.get("url").asText();
            }

            return new Attraction(name, category, rating, description, imageUrl, websiteUrl);

        } catch (Exception e) {
            return null;
        }
    }
}