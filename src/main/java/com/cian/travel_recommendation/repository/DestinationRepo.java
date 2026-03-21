package com.cian.travel_recommendation.repository;

import com.cian.travel_recommendation.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DestinationRepo extends JpaRepository<Destination, Long> {

    List<Destination> findByCountryName(String countryName);

    List<Destination> findByCountryNameContainingIgnoreCase(String countryName);

    List<Destination> findByCityNameContainingIgnoreCaseOrCountryNameContainingIgnoreCase(
            String city,
            String country
    );
}