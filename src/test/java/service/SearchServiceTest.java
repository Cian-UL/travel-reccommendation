package service;

import com.cian.travel_recommendation.dto.SearchResult;
import com.cian.travel_recommendation.entity.Destination;
import com.cian.travel_recommendation.repository.DestinationRepo;
import com.cian.travel_recommendation.service.FlightPriceService;
import com.cian.travel_recommendation.service.SearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {
    @Mock
    private DestinationRepo destinationRepo;

    @Mock
    private FlightPriceService flightPriceService;

    @InjectMocks
    private SearchService searchService;

    @Test
    void destinationWithinBudgetAppearsInResults() {
        Destination destination = new Destination();
        destination.setCityName("Budapest");
        destination.setCountryName("Hungary");
        destination.setAccommodationCostPerNight(new BigDecimal("60.00"));
        destination.setDailyCost(new BigDecimal("65.00"));

        when(destinationRepo.findAll()).thenReturn(List.of(destination));
        when(flightPriceService.getFlightPrice("Dublin", "Budapest"))
                .thenReturn(new BigDecimal("70.00"));

        List<SearchResult> results = searchService.findDestinationsWithinBudget(
                new BigDecimal("1000.00"), "Dublin", 2, 2
        );

        assertEquals(1, results.size());
        assertEquals("Budapest", results.get(0).getDestination().getCityName());
    }

    @Test
    void destinationOverBudgetNotPresent(){
        Destination destination = new Destination();
        destination.setCityName("Budapest");
        destination.setCountryName("Hungary");
        destination.setAccommodationCostPerNight(new BigDecimal("60.00"));
        destination.setDailyCost(new BigDecimal("65.00"));

        when(destinationRepo.findAll()).thenReturn(List.of(destination));
        when(flightPriceService.getFlightPrice("Dublin", "Budapest"))
                .thenReturn(new BigDecimal("70.00"));

        List<SearchResult> results = searchService.findDestinationsWithinBudget(
                new BigDecimal("50.00"), "Dublin", 2, 2
        );
        assertTrue(results.isEmpty());
    }

    @Test
    void resultsSortedCheapestFirst() {
        Destination destination = new Destination();
        destination.setCityName("Budapest");
        destination.setCountryName("Hungary");
        destination.setAccommodationCostPerNight(new BigDecimal("60.00"));
        destination.setDailyCost(new BigDecimal("65.00"));

        Destination destination2 = new Destination();
        destination2.setCityName("Warsaw");
        destination2.setCountryName("Poland");
        destination2.setAccommodationCostPerNight(new BigDecimal("40.00"));
        destination2.setDailyCost(new BigDecimal("45.00"));

        when(destinationRepo.findAll()).thenReturn(List.of(destination, destination2));
        when(flightPriceService.getFlightPrice("Dublin", "Budapest"))
                .thenReturn(new BigDecimal("70.00"));
        when(flightPriceService.getFlightPrice("Dublin", "Warsaw"))
                .thenReturn(new BigDecimal("50.00"));

        List<SearchResult> results = searchService.findDestinationsWithinBudget(
                new BigDecimal("1000.00"), "Dublin", 2, 2
        );

        assertEquals("Warsaw", results.get(0).getDestination().getCityName());
    }

    @Test
    void multipliedFlightCostPerTraveller(){
        Destination destination = new Destination();
        destination.setCityName("Budapest");
        destination.setCountryName("Hungary");
        destination.setAccommodationCostPerNight(new BigDecimal("60.00"));
        destination.setDailyCost(new BigDecimal("65.00"));

        when(destinationRepo.findAll()).thenReturn(List.of(destination));
        when(flightPriceService.getFlightPrice("Dublin", "Budapest"))
        .thenReturn(new BigDecimal("70.00"));

        List<SearchResult> results = searchService.findDestinationsWithinBudget(
                new BigDecimal("5000.00"), "Dublin", 1, 2
        );

        assertEquals(new BigDecimal("140.00"),results.get(0).getFlightCost());
    }

    @Test
    void multipliedAccommodationPerTraveller(){
        Destination destination = new Destination();
        destination.setCityName("Budapest");
        destination.setCountryName("Hungary");
        destination.setAccommodationCostPerNight(new BigDecimal("60.00"));
        destination.setDailyCost(new BigDecimal("65.00"));

        when(destinationRepo.findAll()).thenReturn(List.of(destination));
        when(flightPriceService.getFlightPrice("Dublin", "Budapest"))
                .thenReturn(new BigDecimal("70.00"));

        List<SearchResult> results = searchService.findDestinationsWithinBudget(
                new BigDecimal("5000.00"), "Dublin", 2, 2
        );

        assertEquals(new BigDecimal("240.00"),results.get(0).getAccommodationCost());
    }

    @Test
    void multipliedDailyCostPerTraveller(){
        Destination destination = new Destination();
        destination.setCityName("Budapest");
        destination.setCountryName("Hungary");
        destination.setAccommodationCostPerNight(new BigDecimal("60.00"));
        destination.setDailyCost(new BigDecimal("65.00"));

        when(destinationRepo.findAll()).thenReturn(List.of(destination));
        when(flightPriceService.getFlightPrice("Dublin", "Budapest"))
                .thenReturn(new BigDecimal("70.00"));

        List<SearchResult> results = searchService.findDestinationsWithinBudget(
                new BigDecimal("5000.00"), "Dublin", 2, 2
        );

        assertEquals(new BigDecimal("260.00"),results.get(0).getDailyCost());
    }
}
