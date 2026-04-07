package service;


import com.cian.travel_recommendation.service.MockFlightPriceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

public class MockFlightPriceServiceTest {
    private MockFlightPriceService flightPriceService;
    @BeforeEach
    void setUp() {
        flightPriceService = new MockFlightPriceService();
    }

    @Test
    void knownOriginAndDestination() {
        String origin = "Dublin";
        String destination = "Paris";

        BigDecimal price = flightPriceService.getFlightPrice(origin,destination);

        assertEquals(new BigDecimal("150.00"), price);
    }

    @Test
    void unknownOrigin() {
        String origin = "London";
        String destination = "Paris";

        BigDecimal price = flightPriceService.getFlightPrice(origin,destination);

        assertEquals(new BigDecimal("180.00"), price);
    }

    @Test
    void unknownDestination() {
        String origin = "Dublin";
        String destination = "Test";

        BigDecimal price = flightPriceService.getFlightPrice(origin,destination);

        assertEquals(new BigDecimal("120.00"), price);
    }

    @Test
    void caseSensitive() {
        String origin = "DUBLIN";
        String destination = "Paris";

        BigDecimal price = flightPriceService.getFlightPrice(origin,destination);

        assertEquals(new BigDecimal("150.00"), price);
    }


}
