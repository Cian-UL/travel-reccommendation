INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude)
SELECT 'Paris', 'France', 180.00, 95.00, 85.00, 48.8566, 2.3522
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Paris');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude)
SELECT 'Rome', 'Italy', 150.00, 85.00, 95.00, 41.9028, 12.4964
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Rome');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude)
SELECT 'Barcelona', 'Spain', 140.00, 80.00, 80.00, 41.3874, 2.1686
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Barcelona');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude)
SELECT 'Amsterdam', 'Netherlands', 190.00, 100.00, 75.00, 52.3676, 4.9041
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Amsterdam');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude)
SELECT 'Prague', 'Czech Republic', 110.00, 65.00, 70.00, 50.0755, 14.4378
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Prague');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude)
SELECT 'Vienna', 'Austria', 160.00, 85.00, 90.00, 48.2082, 16.3738
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Vienna');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude)
SELECT 'Budapest', 'Hungary', 100.00, 60.00, 65.00, 47.4979, 19.0402
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Budapest');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude)
SELECT 'Lisbon', 'Portugal', 130.00, 75.00, 85.00, 38.7223, -9.1393
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Lisbon');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude)
SELECT 'Berlin', 'Germany', 140.00, 80.00, 75.00, 52.5200, 13.4050
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Berlin');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude)
SELECT 'Athens', 'Greece', 120.00, 70.00, 55.00, 37.9838, 23.7275
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Athens');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude)
SELECT 'Copenhagen', 'Denmark', 200.00, 110.00, 110.00, 55.6761, 12.5683
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Copenhagen');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude)
SELECT 'Venice', 'Italy', 170.00, 95.00, 100.00, 45.4408, 12.3155
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Venice');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude)
SELECT 'Dubrovnik', 'Croatia', 160.00, 90.00, 130.00, 42.6507, 18.0944
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Dubrovnik');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude)
SELECT 'Reykjavik', 'Iceland', 220.00, 120.00, 150.00, 64.1466, -21.9426
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Reykjavik');

INSERT INTO destination (city_name, country_name, flight_cost_estimate, accommodation_cost_per_night, daily_cost, latitude, longitude)
SELECT 'Zurich', 'Switzerland', 240.00, 130.00, 140.00, 47.3769, 8.5417
    WHERE NOT EXISTS (SELECT 1 FROM destination WHERE city_name = 'Zurich');