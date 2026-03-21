package com.cian.travel_recommendation.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="DESTINATION")
public class Destination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="city_name", length = 100, nullable = false)
    private String cityName;

    @Column(name="country_name", length = 100, nullable = false)
    private String countryName;

    @Column(name = "flight_cost_estimate", precision = 10, scale = 2)
    private BigDecimal flightCostEstimate;

    @Column(name = "accommodation_cost_per_night", precision = 10, scale = 2, nullable = false)
    private BigDecimal accommodationCostPerNight;

    @Column(name = "daily_cost", precision = 100, scale = 2, nullable = false)
    private BigDecimal dailyCost;

    public Long getId() { return id; }
    public String getCityName() { return cityName; }
    public String getCountryName() { return countryName; }
    public BigDecimal getFlightCostEstimate() { return flightCostEstimate; }
    public BigDecimal getAccommodationCostPerNight() { return accommodationCostPerNight; }
    public BigDecimal getDailyCost() { return dailyCost; }

    public void setId(Long id) { this.id = id; }
    public void setCityName(String cityName) { this.cityName = cityName; }
    public void setCountryName(String countryName) { this.countryName = countryName; }
    public void setFlightCostEstimate(BigDecimal flightCostEstimate) { this.flightCostEstimate = flightCostEstimate; }
    public void setAccommodationCostPerNight(BigDecimal accommodationCostPerNight) { this.accommodationCostPerNight = accommodationCostPerNight; }
    public void setDailyCost(BigDecimal dailyCost) { this.dailyCost = dailyCost; }
}