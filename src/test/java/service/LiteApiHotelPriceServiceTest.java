package service;

import com.cian.travel_recommendation.dto.HotelPrice;
import com.cian.travel_recommendation.service.LiteApiHotelPriceService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for LiteApiHotelPriceService
public class LiteApiHotelPriceServiceTest {

    // Test that an invalid API key results in empty list
    @Test
    void returnsEmptyListWhenApiKeyInvalid() {
        LiteApiHotelPriceService service = new LiteApiHotelPriceService("invalid-key");

        List<HotelPrice> prices = service.getHotelPrices(47.4979, 19.0402, "2026-05-01", "2026-05-02", 1);

        assertTrue(prices.isEmpty());
    }

    // Test that invalid coordinates result in empty list
    @Test
    void returnsEmptyListForInvalidCoordinates() {
        LiteApiHotelPriceService service = new LiteApiHotelPriceService("invalid-key");

        List<HotelPrice> prices = service.getHotelPrices(0.0, 0.0, "2026-05-01", "2026-05-02", 1);

        assertTrue(prices.isEmpty());
    }
}