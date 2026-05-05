package com.cian.travel_recommendation.dto;

// Represents hotel pricing information from LiteAPI
public class HotelPrice {

    private String name;
    private double pricePerNight;
    private int stars;
    private double rating;
    private String imageUrl;
    private String address;
    private String tag;

    public HotelPrice(String name, double pricePerNight, int stars, double rating, String imageUrl, String address) {
        this.name = name;
        this.pricePerNight = pricePerNight;
        this.stars = stars;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.address = address;
    }

    public String getName() { return name; }
    public double getPricePerNight() { return pricePerNight; }
    public int getStars() { return stars; }
    public double getRating() { return rating; }
    public String getImageUrl() { return imageUrl; }
    public String getAddress() { return address; }
    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }

    // Display star rating as star characters
    public String getStarsDisplay() {
        return "★".repeat(stars);
    }
}