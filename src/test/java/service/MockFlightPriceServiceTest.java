package service;

import com.cian.travel_recommendation.service.MockFlightPriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

// Unit tests for MockFlightPriceService
public class MockFlightPriceServiceTest {
    private MockFlightPriceService flightPriceService;

    @BeforeEach
    void setUp() {
        flightPriceService = new MockFlightPriceService();
    }

    // Test pricing for known origin and destination
    @Test
    void knownOriginAndDestination() {
        String origin = "Dublin";
        String destination = "Paris";

        BigDecimal price = flightPriceService.getFlightPrice(origin,destination);

        assertEquals(new BigDecimal("150.00"), price);
    }

    // Test default pricing when origin is not in adjustment map
    @Test
    void unknownOrigin() {
        String origin = "London";
        String destination = "Paris";

        BigDecimal price = flightPriceService.getFlightPrice(origin,destination);

        assertEquals(new BigDecimal("180.00"), price);
    }

    // Test default pricing when destination is not in price map
    @Test
    void unknownDestination() {
        String origin = "Dublin";
        String destination = "Test";

        BigDecimal price = flightPriceService.getFlightPrice(origin,destination);

        assertEquals(new BigDecimal("120.00"), price);
    }

    // Test that case-insensitive normalization works
    @Test
    void caseSensitive() {
        String origin = "DUBLIN";
        String destination = "Paris";

        BigDecimal price = flightPriceService.getFlightPrice(origin,destination);

        assertEquals(new BigDecimal("150.00"), price);
    }

}