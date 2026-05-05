package com.cian.travel_recommendation.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

// JPA entity representing a travel destination
@Entity
@Table(name="DESTINATION")
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="city_name", length = 100, nullable = false)
    @NotBlank(message = "City name is required")
    private String cityName;

    @Column(name="country_name", length = 100, nullable = false)
    @NotBlank(message = "Country name is required")
    private String countryName;

    @Column(name = "flight_cost_estimate", precision = 10, scale = 2, nullable = false)
    @NotNull
    @DecimalMin(value = "1.0", message = "Flight cost must be at least 1.0")
    private BigDecimal flightCostEstimate;

    @Column(name = "accommodation_cost_per_night", precision = 10, scale = 2, nullable = false)
    @NotNull
    @DecimalMin(value = "1.0", message = "Accommodation cost must be at least 1.0")
    private BigDecimal accommodationCostPerNight;

    @Column(name = "daily_cost", precision = 10, scale = 2, nullable = false)
    @NotNull
    @DecimalMin(value = "1.0", message = "Daily cost must be at least 1.0")
    private BigDecimal dailyCost;

    @Column(name = "latitude", nullable = false)
    @NotNull
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    @NotNull
    private Double longitude;

    @Column(name = "highlights", length = 500)
    private String highlights;

    public Long getId() { return id; }
    public String getCityName() { return cityName; }
    public String getCountryName() { return countryName; }
    public BigDecimal getFlightCostEstimate() { return flightCostEstimate; }
    public BigDecimal getAccommodationCostPerNight() { return accommodationCostPerNight; }
    public BigDecimal getDailyCost() { return dailyCost; }
    public Double getLongitude() {return longitude;}
    public Double getLatitude() {return latitude;}


    public void setId(Long id) { this.id = id; }
    public void setCityName(String cityName) { this.cityName = cityName; }
    public void setCountryName(String countryName) { this.countryName = countryName; }
    public void setFlightCostEstimate(BigDecimal flightCostEstimate) { this.flightCostEstimate = flightCostEstimate; }
    public void setAccommodationCostPerNight(BigDecimal accommodationCostPerNight) { this.accommodationCostPerNight = accommodationCostPerNight; }
    public void setDailyCost(BigDecimal dailyCost) { this.dailyCost = dailyCost; }
    public void setLongitude(Double longitude) {this.longitude = longitude;}
    public void setLatitude(Double latitude) {this.latitude = latitude;}


    public String getHighlights() {
        return highlights;
    }

    public void setHighlights(String highlights) {
        this.highlights = highlights;
    }

    // Parse highlights as a comma-separated list
    public List<String> getHighlightList() {
        if (highlights == null || highlights.isBlank()) {
            return List.of();
        }
        return List.of(highlights.split(","));
    }

}