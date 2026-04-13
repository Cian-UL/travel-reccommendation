package com.cian.travel_recommendation.dto;

public class Attraction {

    private String name;
    private String category;
    private int rating;
    private String description;
    private String imageUrl;
    private String websiteUrl;

    public Attraction(String name, String category, int rating, String description, String imageUrl, String websiteUrl) {
        this.name = name;
        this.category = category;
        this.rating = rating;
        this.description = description;
        this.imageUrl = imageUrl;
        this.websiteUrl = websiteUrl;
    }

    public String getName() { return name; }
    public String getCategory() { return category; }
    public int getRating() { return rating; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getWebsiteUrl() { return websiteUrl; }

    public String getSearchUrl() {
        if (name != null && !name.isBlank()) {
            return "https://www.google.com/search?q=" + name.replace(" ", "+");
        }
        return null;
    }

    public String getBestUrl() {
        if (websiteUrl != null && !websiteUrl.isBlank()) {
            return websiteUrl;
        }
        return getSearchUrl();
    }
}