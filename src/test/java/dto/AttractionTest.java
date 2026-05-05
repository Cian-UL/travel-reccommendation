package dto;

import com.cian.travel_recommendation.dto.Attraction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// Unit tests for Attraction DTO
public class AttractionTest {

    // Test that search URL is generated correctly from attraction name
    @Test
    void searchUrlGeneratedFromName() {
        Attraction attraction = new Attraction("Buda Castle", "historic", 5, null, null, null);

        assertEquals("https://www.google.com/search?q=Buda+Castle", attraction.getSearchUrl());
    }

    // Test that search URL is null when name is blank
    @Test
    void searchUrlNullWhenNameBlank() {
        Attraction attraction = new Attraction("", "historic", 5, null, null, null);

        assertNull(attraction.getSearchUrl());
    }

    // Test that getBestUrl returns official website when available
    @Test
    void bestUrlReturnsWebsiteWhenAvailable() {
        Attraction attraction = new Attraction("Buda Castle", "historic", 5, null, null, "http://castle.hu");

        assertEquals("http://castle.hu", attraction.getBestUrl());
    }

    // Test that getBestUrl falls back to search URL when no website
    @Test
    void bestUrlFallsBackToSearchUrl() {
        Attraction attraction = new Attraction("Buda Castle", "historic", 5, null, null, null);

        assertEquals("https://www.google.com/search?q=Buda+Castle", attraction.getBestUrl());
    }

    // Test that getBestUrl treats blank website as missing
    @Test
    void bestUrlFallsBackWhenWebsiteBlank() {
        Attraction attraction = new Attraction("Buda Castle", "historic", 5, null, null, "   ");

        assertEquals("https://www.google.com/search?q=Buda+Castle", attraction.getBestUrl());
    }

    // Test that all fields are stored and retrieved correctly
    @Test
    void allFieldsStoredCorrectly() {
        Attraction attraction = new Attraction("Parliament", "architecture", 7, "A grand building", "http://img.jpg", "http://parliament.hu");

        assertEquals("Parliament", attraction.getName());
        assertEquals("architecture", attraction.getCategory());
        assertEquals(7, attraction.getRating());
        assertEquals("A grand building", attraction.getDescription());
        assertEquals("http://img.jpg", attraction.getImageUrl());
        assertEquals("http://parliament.hu", attraction.getWebsiteUrl());
    }
}