package repository;

import com.cian.travel_recommendation.TravelRecommendationApplication;
import com.cian.travel_recommendation.entity.Destination;
import com.cian.travel_recommendation.repository.DestinationRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

// Integration tests for DestinationRepo
@SpringBootTest(classes = TravelRecommendationApplication.class)
@Transactional
public class DestinationRepoTest {

    @Autowired
    private DestinationRepo destinationRepo;

    // Test that a destination can be saved and retrieved by ID
    @Test
    void canSaveAndFindDestination() {
        Destination destination = new Destination();
        destination.setCityName("TestPariss");
        destination.setCountryName("TestFrance");
        destination.setFlightCostEstimate(new BigDecimal("150.00"));
        destination.setAccommodationCostPerNight(new BigDecimal("80.00"));
        destination.setDailyCost(new BigDecimal("75.00"));
        destination.setLatitude(48.8566);
        destination.setLongitude(2.3522);
        destination.setHighlights("Eiffel Tower,Louvre");

        Destination saved = destinationRepo.save(destination);
        Optional<Destination> found = destinationRepo.findById(saved.getId());

        assertTrue(found.isPresent());
        assertEquals("TestPariss", found.get().getCityName());
    }

    // Test that findAll returns saved destinations
    @Test
    void findAllReturnsSavedDestinations() {
        Destination d1 = new Destination();
        d1.setCityName("TestRome");
        d1.setCountryName("TestItaly");
        d1.setFlightCostEstimate(new BigDecimal("150.00"));
        d1.setAccommodationCostPerNight(new BigDecimal("80.00"));
        d1.setDailyCost(new BigDecimal("75.00"));
        d1.setLatitude(41.9028);
        d1.setLongitude(12.4964);

        destinationRepo.save(d1);

        List<Destination> all = destinationRepo.findAll();
        assertTrue(all.size() >= 1);
    }

    // Test that findById returns empty for non-existent ID
    @Test
    void findByIdReturnsEmptyForBadId() {
        Optional<Destination> result = destinationRepo.findById(999999L);
        assertTrue(result.isEmpty());
    }

    // Test that a destination can be deleted
    @Test
    void canDeleteDestination() {
        Destination destination = new Destination();
        destination.setCityName("TestLisbon");
        destination.setCountryName("TestPortugal");
        destination.setFlightCostEstimate(new BigDecimal("150.00"));
        destination.setAccommodationCostPerNight(new BigDecimal("80.00"));
        destination.setDailyCost(new BigDecimal("75.00"));
        destination.setLatitude(38.7223);
        destination.setLongitude(-9.1393);

        Destination saved = destinationRepo.save(destination);
        Long id = saved.getId();

        destinationRepo.deleteById(id);

        Optional<Destination> result = destinationRepo.findById(id);
        assertTrue(result.isEmpty());
    }
}