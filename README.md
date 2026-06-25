# Wandr

![CI](https://github.com/Cian-UL/travel-reccommendation/actions/workflows/ci.yml/badge.svg)

**[Live demo →](https://travel-reccommendation-production.up.railway.app/)**

A budget-first European travel recommendation platform.

Wandr inverts the standard online travel agency model. Instead of asking *"where do you want to go?"* and revealing cost progressively, Wandr asks *"how much can you spend?"* and surfaces destinations whose total trip cost - flights, accommodation, and daily living - falls within that budget.

Each destination links to a detail page that aggregates cost information, attraction recommendations from OpenTripMap, and live hotel pricing from LiteAPI into a single three-column layout, removing the need to consult separate flight, hotel, and attraction sites.

This project was built as the dissertation artefact for an MSc in Software Development (International Systems) at the University of Limerick, and has been extended since with containerisation, continuous integration, and a live deployment.

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
- **Docker** and **Docker Compose** for containerised local runs
- **GitHub Actions** for continuous integration
- **Railway** for deployment

---

## Running with Docker (recommended)

The entire application, including its PostgreSQL database, runs with a single command. No local Java, Maven, or PostgreSQL installation is required.

### 1. Clone the repository

```bash
git clone https://github.com/Cian-UL/travel-reccommendation.git
cd travel-recommendation
```

### 2. Provide your API keys

Copy the environment template and fill in your values:

```bash
cp .env.example .env
```

Then edit `.env` and set `DB_PASSWORD` (any value you choose for the local database), `OPENTRIPMAP_KEY`, and `LITEAPI_KEY`. The `.env` file is gitignored and never committed.

### 3. Start everything

```bash
docker compose up --build
```

This builds the application image, starts a PostgreSQL container, waits for it to be healthy, then starts the app. Open [http://localhost:8080](http://localhost:8080) once it has started.

To stop, press `Ctrl+C`, then `docker compose down`. Use `docker compose down -v` to also wipe the database volume for a clean slate.

---

## Running Locally Without Docker

If you would rather run the application directly against a local PostgreSQL instance:

### Prerequisites

- Java 23 or later
- Maven 3.9 or later
- PostgreSQL 18 running locally on port **5433**
- A database named `travel_project_database` owned by user `postgres`
- API keys for OpenTripMap and LiteAPI (both have free tiers)

### 1. Create the database

```sql
CREATE DATABASE travel_project_database;
```

The application uses Hibernate's `ddl-auto=update` mode, so the schema is created automatically on first startup. Seed data for fifteen European destinations is loaded from `src/main/resources/data.sql` using idempotent inserts, so the application can be restarted without producing duplicate rows.

### 2. Set environment variables

The application reads the following environment variables. None of them are committed to the repository.

| Variable | Purpose | Where to get it |
|---|---|---|
| `DB_PASSWORD` | PostgreSQL password for the `postgres` user | Your local setup |
| `OPENTRIPMAP_KEY` | OpenTripMap API key | [opentripmap.io](https://opentripmap.io/product) (free) |
| `LITEAPI_KEY` | LiteAPI sandbox key | [liteapi.travel](https://liteapi.travel) (free sandbox) |

Set them in your shell or your IDE's run configuration. For example, on macOS or Linux:

```bash
export DB_PASSWORD=your_postgres_password
export OPENTRIPMAP_KEY=your_opentripmap_key
export LITEAPI_KEY=your_liteapi_sandbox_key
```

On Windows PowerShell:

```powershell
$env:DB_PASSWORD="your_postgres_password"
$env:OPENTRIPMAP_KEY="your_opentripmap_key"
$env:LITEAPI_KEY="your_liteapi_sandbox_key"
```

### 3. Run the application

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

These tests also run automatically on every push via GitHub Actions, against a real PostgreSQL service container. See `.github/workflows/ci.yml`.

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

### Configuration through environment

Database connection details, the server port, and the external API keys are all read from environment variables, with sensible local defaults. This lets the same build run unchanged on a developer machine, inside Docker Compose, in CI against a service container, and on Railway in production, with each environment supplying its own values.

---

## Deployment

The application is deployed on [Railway](https://railway.app), which builds the image from the `Dockerfile` and runs it alongside a managed PostgreSQL instance. Database credentials and the server port are injected as environment variables at deploy time, and the app reads them through the same configuration described above. Pushes to `master` trigger an automatic redeploy.
