INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude, highlights)
SELECT 'Paris', 'France', 180.00, 95.00, 85.00, 48.8566, 2.3522, 'Eiffel Tower,Louvre Museum,Notre-Dame Cathedral,Arc de Triomphe,Sacré-Cœur'
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Paris');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude, highlights)
SELECT 'Rome', 'Italy', 150.00, 85.00, 95.00, 41.9028, 12.4964, 'Colosseum,Vatican Museums,Pantheon,Trevi Fountain,Roman Forum'
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Rome');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude, highlights)
SELECT 'Barcelona', 'Spain', 140.00, 80.00, 80.00, 41.3874, 2.1686, 'Sagrada Familia,Park Güell,La Rambla,Casa Batlló,Gothic Quarter'
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Barcelona');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude, highlights)
SELECT 'Amsterdam', 'Netherlands', 190.00, 100.00, 75.00, 52.3676, 4.9041, 'Rijksmuseum,Anne Frank House,Van Gogh Museum,Canal Ring,Vondelpark'
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Amsterdam');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude, highlights)
SELECT 'Prague', 'Czech Republic', 110.00, 65.00, 70.00, 50.0755, 14.4378, 'Charles Bridge,Prague Castle,Old Town Square,Astronomical Clock,St Vitus Cathedral'
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Prague');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude, highlights)
SELECT 'Vienna', 'Austria', 160.00, 85.00, 90.00, 48.2082, 16.3738, 'Schönbrunn Palace,St Stephens Cathedral,Belvedere Palace,Vienna State Opera,Hofburg'
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Vienna');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude, highlights)
SELECT 'Budapest', 'Hungary', 100.00, 60.00, 65.00, 47.4979, 19.0402, 'Buda Castle,Hungarian Parliament,Fishermans Bastion,Széchenyi Thermal Bath,Chain Bridge'
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Budapest');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude, highlights)
SELECT 'Lisbon', 'Portugal', 130.00, 75.00, 85.00, 38.7223, -9.1393, 'Belém Tower,Jerónimos Monastery,São Jorge Castle,Alfama District,Praça do Comércio'
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Lisbon');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude, highlights)
SELECT 'Berlin', 'Germany', 140.00, 80.00, 75.00, 52.5200, 13.4050, 'Brandenburg Gate,Berlin Wall Memorial,Museum Island,Reichstag Building,Checkpoint Charlie'
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Berlin');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude, highlights)
SELECT 'Athens', 'Greece', 120.00, 70.00, 55.00, 37.9838, 23.7275, 'Acropolis,Parthenon,Ancient Agora,Temple of Olympian Zeus,Plaka District'
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Athens');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude, highlights)
SELECT 'Copenhagen', 'Denmark', 200.00, 110.00, 110.00, 55.6761, 12.5683, 'Tivoli Gardens,The Little Mermaid,Nyhavn,Christiansborg Palace,Rosenborg Castle'
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Copenhagen');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude, highlights)
SELECT 'Venice', 'Italy', 170.00, 95.00, 100.00, 45.4408, 12.3155, 'St Marks Basilica,Doges Palace,Rialto Bridge,Grand Canal,Piazza San Marco'
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Venice');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude, highlights)
SELECT 'Dubrovnik', 'Croatia', 160.00, 90.00, 130.00, 42.6507, 18.0944, 'City Walls,Old Town,Fort Lovrijenac,Stradun,Lokrum Island'
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Dubrovnik');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude, highlights)
SELECT 'Reykjavik', 'Iceland', 220.00, 120.00, 150.00, 64.1466, -21.9426, 'Hallgrímskirkja,Harpa Concert Hall,Sun Voyager,Golden Circle,Blue Lagoon'
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Reykjavik');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude, highlights)
SELECT 'Zurich', 'Switzerland', 240.00, 130.00, 140.00, 47.3769, 8.5417, 'Lake Zurich,Grossmünster,Bahnhofstrasse,Old Town,Swiss National Museum'
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Zurich');