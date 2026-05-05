# Wandr

A budget-first European travel recommendation platform.

Wandr inverts the standard online travel agency model. Instead of asking *"where do you want to go?"* and revealing cost progressively, Wandr asks *"how much can you spend?"* and surfaces destinations whose total trip cost - flights, accommodation, and daily living - falls within that budget.

Each destination links to a detail page that aggregates cost information, attraction recommendations from OpenTripMap, and live hotel pricing from LiteAPI into a single three-column layout, removing the need to consult separate flight, hotel, and attraction sites.

This project was built as the dissertation artefact for an MSc in Software Development (International Systems) at the University of Limerick.

---

## Features

- **Budget-first search.** Enter a total trip budget, origin city, number of nights, and number of travellers. Get back a sorted list of European destinations that fit.
- **Cost breakdown per destination.** Every result shows flight, accommodation, and daily-spend components, not just a total.
- **Live data on the detail page.** The destination detail page combines stored cost estimates with live attractions from OpenTripMap and live hotel prices from LiteAPI.
- **Curated hotel selection.** Hotels are tagged as Best Value, Top Rated, Luxury, or Mid-Range so the user sees a meaningful spread rather than a long list.
- **Graceful degradation.** When external APIs return nothing (or fail entirely), each panel renders an empty state rather than breaking the page.

---

## Tech Stack

- **Java 23**
- **Spring Boot 4.0.1** - Spring Web, Spring Data JPA, Validation, DevTools
- **PostgreSQL 18** with **Hibernate 7.2**
- **Thymeleaf** for server-side rendering
- **Bootstrap 5.3.3** with Google Fonts (Playfair Display, DM Sans)
- **Spring RestClient** with **Jackson 3** for external API calls
- **JUnit 5** and **Mockito** for testing
- **Maven 3.9.9** for build management

---

## Prerequisites

You will need:

- Java 23 or later
- Maven 3.9 or later
- PostgreSQL 18 running locally on port **5433**
- A database named `travel_project_database` owned by user `postgres`
- API keys for OpenTripMap and LiteAPI (both have free tiers)

---

## Setup

### 1. Clone the repository

```bash
git clone <repository-url>
cd travel_recommendation
```

### 2. Create the database

Connect to your local PostgreSQL instance and create the database:

```sql
CREATE DATABASE travel_project_database;
```

The application uses Hibernate's `ddl-auto=update` mode, so the schema will be created automatically on first startup. Seed data for fifteen European destinations is loaded from `src/main/resources/data.sql` using idempotent inserts, so the application can be restarted without producing duplicate rows.

### 3. Set environment variables

The application reads three environment variables. None of them are committed to the repository.

| Variable | Purpose | Where to get it |
|---|---|---|
| `DB_PASSWORD` | PostgreSQL password for the `postgres` user | Your local setup |
| `OPENTRIPMAP_KEY` | OpenTripMap API key | [opentripmap.io](https://opentripmap.io/product) (free) |
| `LITEAPI_KEY` | LiteAPI sandbox key | [liteapi.travel](https://liteapi.travel) (free sandbox) |

Set them in your shell, your IDE's run configuration, or an `.env` file (not committed). For example, on macOS or Linux:

```bash
export DB_PASSWORD=your_postgres_password
export OPENTRIPMAP_KEY=your_opentripmap_key
export LITEAPI_KEY=your_liteapi_sandbox_key
```

### 4. Run the application

```bash
mvn spring-boot:run
```

Open [http://localhost:8080](http://localhost:8080) in a browser.

---

## Running the Tests

```bash
mvn test
```

The test suite contains twenty-eight tests across seven classes:

- `MockFlightPriceServiceTest` - flight price lookup and fallback behaviour
- `SearchServiceTest` - budget filtering and cost calculation
- `LiteApiHotelPriceServiceTest` - defensive parsing of LiteAPI responses
- `HotelPriceTest` - DTO helpers including star rendering and search URL fallback
- `AttractionTest` - DTO helpers including the `getBestUrl` chain
- `DestinationRepoTest` - JPA persistence against the live database
- `TravelRecommendationApplicationTests` - Spring application context loads

Service and DTO tests run in milliseconds using Mockito stubs. Repository tests load the full Spring context and require the database to be running.

---

## Project Structure

```
src/main/java/com/cian/travel_recommendation/
├── controller/
│   ├── SearchController.java
│   ├── DestinationDetailController.java
│   └── GlobalExceptionHandler.java
├── service/
│   ├── SearchService.java
│   ├── FlightPriceService.java
│   ├── MockFlightPriceService.java
│   ├── AttractionService.java
│   ├── OpenTripMapAttractionService.java
│   ├── HotelPriceService.java
│   └── LiteApiHotelPriceService.java
├── repository/
│   └── DestinationRepo.java
├── model/
│   └── Destination.java
└── dto/
    ├── SearchResult.java
    ├── Attraction.java
    └── HotelPrice.java

src/main/resources/
├── application.properties
├── data.sql
├── static/
│   └── css/
└── templates/
    ├── search.html
    ├── search-results.html
    ├── destination-detail.html
    └── error.html

src/test/java/
├── service/
├── dto/
└── repository/
```

---

## Architecture Notes

### Interface-based external services

Three of the platform's services follow an interface-based pattern: `FlightPriceService`, `AttractionService`, and `HotelPriceService` are interfaces that define contracts for retrieving data from external sources. Concrete implementations (`MockFlightPriceService`, `OpenTripMapAttractionService`, `LiteApiHotelPriceService`) provide the actual behaviour. Consumers depend on the interfaces rather than the implementations, which means a different provider can be substituted by adding a new class without changes elsewhere.

### Mock flight pricing

`MockFlightPriceService` returns deterministic prices based on hardcoded base values per destination, with per-origin adjustments for Dublin, Cork, and Shannon. The Amadeus self-service API was the original intended provider but its registration was closed during the development period. Because the service implements `FlightPriceService`, swapping in a live provider (Amadeus, Duffel, Kiwi.com, etc.) is a matter of writing a new implementation class and adjusting the bean configuration. No other code needs to change.

### LiteAPI sandbox mode

The application is configured against the LiteAPI sandbox environment, which returns realistic response structures suitable for development and demonstration. The sandbox is rate-limited to 5 requests per second.

### In-memory budget filtering

The current `SearchService` implementation loads all destinations from the database and filters in memory. This is acceptable at the current scale of fifteen destinations and keeps the cost calculation in a single testable location, but would not scale to a larger catalogue. Pushing the filter into the database (e.g., a derived query method on `DestinationRepo`) is identified as a future improvement.
