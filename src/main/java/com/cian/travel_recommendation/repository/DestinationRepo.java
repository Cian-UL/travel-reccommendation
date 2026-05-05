package com.cian.travel_recommendation.repository;

import com.cian.travel_recommendation.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// Spring Data JPA repository for Destination entity
@Repository
public interface DestinationRepo extends JpaRepository<Destination, Long> {

    // Find all destinations in a specific country (exact match)
    List<Destination> findByCountryName(String countryName);

    // Find destinations by partial country name (case-insensitive)
    List<Destination> findByCountryNameContainingIgnoreCase(String countryName);

    // Find destinations by partial city or country name (case-insensitive)
    List<Destination> findByCityNameContainingIgnoreCaseOrCountryNameContainingIgnoreCase(
            String city,
            String country
    );
}