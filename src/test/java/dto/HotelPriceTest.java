package dto;

import com.cian.travel_recommendation.dto.HotelPrice;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for HotelPrice DTO
public class HotelPriceTest {

    // Test that star rating is formatted as star characters
    @Test
    void starsDisplayReturnsCorrectStars() {
        HotelPrice hotel = new HotelPrice("Test Hotel", 100.0, 4, 8.5, null, "Test Address");

        assertEquals("★★★★", hotel.getStarsDisplay());
    }

    // Test that zero stars displays as empty string
    @Test
    void starsDisplayReturnsEmptyForZeroStars() {
        HotelPrice hotel = new HotelPrice("Test Hotel", 100.0, 0, 8.5, null, "Test Address");

        assertEquals("", hotel.getStarsDisplay());
    }

    // Test that tag defaults to null
    @Test
    void tagDefaultsToNull() {
        HotelPrice hotel = new HotelPrice("Test Hotel", 100.0, 4, 8.5, null, "Test Address");

        assertNull(hotel.getTag());
    }

    // Test that tag can be set and retrieved
    @Test
    void tagCanBeSet() {
        HotelPrice hotel = new HotelPrice("Test Hotel", 100.0, 4, 8.5, null, "Test Address");
        hotel.setTag("Best Value");

        assertEquals("Best Value", hotel.getTag());
    }

    // Test that all fields are stored and retrieved correctly
    @Test
    void allFieldsStoredCorrectly() {
        HotelPrice hotel = new HotelPrice("Grand Hotel", 250.50, 5, 9.2, "http://img.jpg", "Main St 1");

        assertEquals("Grand Hotel", hotel.getName());
        assertEquals(250.50, hotel.getPricePerNight());
        assertEquals(5, hotel.getStars());
        assertEquals(9.2, hotel.getRating());
        assertEquals("http://img.jpg", hotel.getImageUrl());
        assertEquals("Main St 1", hotel.getAddress());
    }
}